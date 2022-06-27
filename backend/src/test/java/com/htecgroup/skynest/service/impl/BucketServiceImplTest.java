package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.utils.UserEntityUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BucketServiceImplTest {

  @Test
  void listAllBuckets() {
    List<BucketEntity> bucketEntityList = new ArrayList<>();
    bucketEntityList.add(UserEntityUtil.getVerified());
    when(bucketRepository.findAll()).thenReturn(bucketEntityList);

    List<BucketEntity> expectedResponse = new ArrayList<>(bucketEntityList);

    List<BucketResponse> actualResponse = bucketService.listAllUsers();

    Assertions.assertEquals(expectedResponse.size(), actualResponse.size());
    this.assertUserEntityAndUserResponse(expectedResponse.get(0), actualResponse.get(0));
  }
}
