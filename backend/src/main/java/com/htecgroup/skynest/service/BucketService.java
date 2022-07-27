package com.htecgroup.skynest.service;

import com.htecgroup.skynest.lambda.LambdaType;
import com.htecgroup.skynest.model.dto.BucketDto;
import com.htecgroup.skynest.model.request.BucketCreateRequest;
import com.htecgroup.skynest.model.request.BucketEditRequest;
import com.htecgroup.skynest.model.response.BucketResponse;
import com.htecgroup.skynest.model.response.StorageContentResponse;

import java.util.List;
import java.util.UUID;

public interface BucketService {

  BucketResponse createBucket(BucketCreateRequest bucketCreateRequest);

  BucketResponse getBucketDetails(UUID uuid);

  List<BucketResponse> listAllBuckets();

  BucketDto findBucketById(UUID uuid);

  void deleteBucket(UUID uuid);

  void restoreBucket(UUID uuid);

  BucketResponse editBucket(BucketEditRequest bucketEditRequest, UUID uuid);

  StorageContentResponse getBucketContent(UUID bucketId);

  BucketDto findBucketByName(String name);

  List<LambdaType> getActiveLambdas(UUID bucketId);

  void deactivateLambda(UUID bucketId, LambdaType lambda);

  void activateLambda(UUID bucketId, LambdaType lambdaType);

  List<BucketResponse> getAllBucketsWithTag(UUID tagId, UUID loggedUserId);
}
