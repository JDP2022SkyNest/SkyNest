package com.htecgroup.skynest.service;

import com.htecgroup.skynest.annotation.CanBucketBeEdited;
import com.htecgroup.skynest.model.request.BucketEditRequest;
import com.htecgroup.skynest.model.response.BucketResponse;

import javax.validation.Valid;
import java.util.UUID;

public interface BucketService {

  BucketResponse getBucket(UUID uuid);

  BucketResponse editBucket(
      BucketEditRequest bucketEditRequest, @Valid @CanBucketBeEdited UUID uuid);
}
