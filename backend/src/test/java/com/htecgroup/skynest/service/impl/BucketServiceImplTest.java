package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.buckets.BucketAlreadyDeletedException;
import com.htecgroup.skynest.model.dto.BucketDto;
import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.response.BucketResponse;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.utils.BucketDtoUtil;
import com.htecgroup.skynest.utils.BucketEntityUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BucketServiceImplTest {

  @Mock private BucketRepository bucketRepository;
  @Spy private ModelMapper modelMapper;
  @Spy @InjectMocks private BucketServiceImpl bucketService;

  @Captor private ArgumentCaptor<BucketEntity> captorBucketEntity;

  @Test
  void getBucket() {
    BucketEntity bucketEntity = BucketEntityUtil.getPrivateBucket();
    when(bucketRepository.findById(any())).thenReturn(Optional.of(bucketEntity));

    BucketResponse actualBucketResponse = bucketService.getBucket(bucketEntity.getId());

    Assertions.assertEquals(actualBucketResponse.getName(), bucketEntity.getName());
    Assertions.assertEquals(actualBucketResponse.getDescription(), bucketEntity.getDescription());
    Assertions.assertEquals(actualBucketResponse.getSize(), bucketEntity.getSize());
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
