package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.dto.BucketDto;
import com.htecgroup.skynest.model.response.BucketResponse;

import java.util.UUID;

public interface BucketService {

  BucketResponse getBucket(UUID uuid);

  BucketDto findBucketById(UUID uuid);

  void deleteBucket(UUID uuid);
}
