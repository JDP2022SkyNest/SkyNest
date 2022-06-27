package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.model.response.BucketResponse;
import com.htecgroup.skynest.utils.UserEntityUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BucketServiceImplTest {

  @Mock private BucketRepository bucketRepository;
  @Spy @InjectMocks private BucketServiceImpl bucketService;

  @Test
  void getBucket() {
    BucketEntity bucketEntity = UserEntityUtil.getNotVerified();
    when(bucketRepository.findById(any())).thenReturn(Optional.of(bucketEntity));

    BucketResponse actualBucketResponse = bucketService.getUser(bucketEntity.getId());

    this.assertUserEntityAndUserResponse(userEntity, actualUserResponse);
    verify(userRepository, times(1)).findById(any());
  }
}
