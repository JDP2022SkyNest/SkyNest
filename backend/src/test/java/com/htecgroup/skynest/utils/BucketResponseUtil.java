package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.response.BucketResponse;

import java.util.UUID;

public class BucketResponseUtil {
  public static BucketResponse getCurrentPrivate() {
    return new BucketResponse(
        BucketEntityUtil.getPrivateBucket().getId(),
        BucketEntityUtil.getPrivateBucket().getCreatedBy().getId(),
        "Name",
        BucketDtoUtil.companyDto.getId(),
        "Description",
        1000L,
        false);
  }

  public static BucketResponse getOtherPrivate() {
    BucketResponse bucketResponse = getCurrentPrivate();
    bucketResponse.setCreatedById(UUID.randomUUID());
    return bucketResponse;
  }
}
