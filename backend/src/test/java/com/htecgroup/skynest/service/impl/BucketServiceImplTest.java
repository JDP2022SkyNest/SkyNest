package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.buckets.BucketAlreadyDeletedException;
import com.htecgroup.skynest.model.dto.BucketDto;
import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.request.BucketCreateRequest;
import com.htecgroup.skynest.model.request.BucketEditRequest;
import com.htecgroup.skynest.model.response.BucketResponse;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.utils.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BucketServiceImplTest {

  @Mock private BucketRepository bucketRepository;
  @Mock private CurrentUserService currentUserService;
  @Mock private UserRepository userRepository;
  @Spy private ModelMapper modelMapper;
  @Spy @InjectMocks private BucketServiceImpl bucketService;

  @Captor private ArgumentCaptor<BucketEntity> captorBucketEntity;

  @Test
  void listAllBuckets() {
    List<BucketEntity> bucketEntityList =
        Collections.singletonList(BucketEntityUtil.getPrivateBucket());
    when(bucketRepository.findAll()).thenReturn(bucketEntityList);

    List<BucketEntity> expectedResponse = new ArrayList<>(bucketEntityList);

    List<BucketResponse> actualResponse = bucketService.listAllBuckets();

    Assertions.assertEquals(expectedResponse.size(), actualResponse.size());
    this.assertBucketEntityAndBucketResponse(expectedResponse.get(0), actualResponse.get(0));
  }

  @Test
  void getBucket() {
    BucketEntity expectedBucketEntity = BucketEntityUtil.getPrivateBucket();
    when(bucketRepository.findById(any())).thenReturn(Optional.of(expectedBucketEntity));

    BucketResponse actualBucketResponse = bucketService.getBucket(expectedBucketEntity.getId());

    this.assertBucketEntityAndBucketResponse(expectedBucketEntity, actualBucketResponse);
  }

  @Test
  void createBucket() {
    BucketEntity expectedBucketEntity = BucketEntityUtil.getPrivateBucket();
    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedWorkerUser());
    when(userRepository.getById(any())).thenReturn(UserEntityUtil.getVerified());
    when(bucketRepository.save(any())).thenReturn(expectedBucketEntity);

    BucketCreateRequest bucketCreateRequest = BucketCreateRequestUtil.get();
    BucketResponse actualBucketResponse = bucketService.createBucket(bucketCreateRequest);
    this.assertBucketEntityAndBucketResponse(expectedBucketEntity, actualBucketResponse);
  }

  @Test
  void editBucket() {
    BucketEntity expectedBucketEntity = BucketEntityUtil.getPrivateBucket();
    when(bucketRepository.findById(any())).thenReturn(Optional.of(expectedBucketEntity));
    when(bucketRepository.save(any())).thenReturn(expectedBucketEntity);

    BucketEditRequest bucketEditRequest = BucketEditRequestUtil.get();
    BucketResponse actualBucketResponse =
        bucketService.editBucket(bucketEditRequest, expectedBucketEntity.getId());
    this.assertBucketEntityAndBucketResponse(expectedBucketEntity, actualBucketResponse);
  }

  private void assertBucketEntityAndBucketResponse(
      BucketEntity expectedBucketEntity, BucketResponse actualBucketResponse) {
    Assertions.assertEquals(
        expectedBucketEntity.getCreatedBy().getId(), actualBucketResponse.getCreatedById());
    Assertions.assertEquals(expectedBucketEntity.getName(), actualBucketResponse.getName());
    Assertions.assertEquals(
        expectedBucketEntity.getCompany().getId(), actualBucketResponse.getCompanyId());
    Assertions.assertEquals(
        expectedBucketEntity.getDescription(), actualBucketResponse.getDescription());
    Assertions.assertEquals(expectedBucketEntity.getSize(), actualBucketResponse.getSize());
    Assertions.assertEquals(expectedBucketEntity.getIsPublic(), actualBucketResponse.getIsPublic());
  }

  @Test
  void when_AlreadyDeletedBucket_deletedBucket_ShouldThrowBucketAlreadyDeleted() {
    BucketDto bucketDto = BucketDtoUtil.getDeletedBucket();
    doReturn(bucketDto).when(bucketService).findBucketById(any());
    String expectedErrorMessage = BucketAlreadyDeletedException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            BucketAlreadyDeletedException.class,
            () -> bucketService.deleteBucket(UUID.randomUUID()));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void when_deleteBucket_ShouldDeleteBucket() {
    BucketDto bucketDto = BucketDtoUtil.getNotDeletedBucket();
    doReturn(bucketDto).when(bucketService).findBucketById(any());

    bucketService.deleteBucket(UUID.randomUUID());
    Mockito.verify(bucketRepository).save(captorBucketEntity.capture());

    BucketEntity bucketEntity = captorBucketEntity.getValue();
    Assertions.assertNotNull(bucketEntity.getDeletedOn());
  }
}
