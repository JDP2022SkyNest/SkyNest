package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.model.response.BucketResponse;
import com.htecgroup.skynest.service.BucketService;

import java.util.UUID;

public class BucketServiceImpl implements BucketService {

  private BucketsRepository bucketsRepository;

  @Override
  public BucketResponse getBucket(UUID uuid) {
    BucketEntity bucketEntity = bucketsRepository.getById(uuid);
    BucketResponse bucketResponse = modelMapper.map(bucketEntity, BucketResponse.class);
    return bucketResponse;
  }
}
