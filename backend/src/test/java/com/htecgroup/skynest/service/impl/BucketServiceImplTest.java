package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.response.BucketResponse;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.utils.BucketEntityUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BucketServiceImplTest {

  @Mock private BucketRepository bucketRepository;
  @Spy private ModelMapper modelMapper;
  @Spy @InjectMocks private BucketServiceImpl bucketService;

  @Test
  void listAllBuckets() {
    List<BucketEntity> bucketEntityList = new ArrayList<>();
    bucketEntityList.add(BucketEntityUtil.getPrivateBucket());
    when(bucketRepository.findAll()).thenReturn(bucketEntityList);

    List<BucketEntity> expectedResponse = new ArrayList<>(bucketEntityList);

    List<BucketResponse> actualResponse = bucketService.listAllBuckets();

    Assertions.assertEquals(expectedResponse.size(), actualResponse.size());
  }

  @Test
  void getBucket() {
    BucketEntity bucketEntity = BucketEntityUtil.getPrivateBucket();
    when(bucketRepository.findById(any())).thenReturn(Optional.of(bucketEntity));

    BucketResponse actualBucketResponse = bucketService.getBucket(bucketEntity.getId());

    Assertions.assertEquals(actualBucketResponse.getName(), bucketEntity.getName());
    Assertions.assertEquals(actualBucketResponse.getDescription(), bucketEntity.getDescription());
    Assertions.assertEquals(actualBucketResponse.getSize(), bucketEntity.getSize());
  }
}
