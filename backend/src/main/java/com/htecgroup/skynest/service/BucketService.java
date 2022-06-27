package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.response.BucketResponse;

import java.util.List;

public interface BucketService {
  List<BucketResponse> listAllBuckets();
}
