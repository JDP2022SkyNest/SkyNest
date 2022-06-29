package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.response.BucketResponse;

import java.util.List;
import java.util.UUID;

public interface BucketService {

  BucketResponse getBucket(UUID uuid);

  List<BucketResponse> listAllBuckets();
}
