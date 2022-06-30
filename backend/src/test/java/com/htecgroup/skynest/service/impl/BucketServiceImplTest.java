package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.request.BucketCreateRequest;
import com.htecgroup.skynest.model.response.BucketResponse;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.utils.BucketCreateRequestUtil;
import com.htecgroup.skynest.utils.BucketEntityUtil;
import com.htecgroup.skynest.utils.LoggedUserDtoUtil;
import com.htecgroup.skynest.utils.UserEntityUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BucketServiceImplTest {

  @Mock private BucketRepository bucketRepository;
  @Mock private CurrentUserService currentUserService;
  @Mock private UserRepository userRepository;
  @Spy private ModelMapper modelMapper;
  @Spy @InjectMocks private BucketServiceImpl bucketService;

  @Test
  void listAllBuckets() {
    List<BucketEntity> bucketEntityList =
        Collections.singletonList(BucketEntityUtil.getPrivateBucket());
    when(bucketRepository.findAll()).thenReturn(bucketEntityList);

    List<BucketEntity> expectedResponse = new ArrayList<>(bucketEntityList);

    List<BucketResponse> actualResponse = bucketService.listAllBuckets();

    Assertions.assertEquals(expectedResponse.size(), actualResponse.size());
    Assertions.assertEquals(
        expectedResponse.get(0).getId(), actualResponse.get(0).getCreatedById());
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
}
