package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.request.BucketCreateRequest;
import com.htecgroup.skynest.model.response.BucketResponse;

public interface BucketService {

  BucketResponse createBucket(BucketCreateRequest bucketCreateRequest);
}
