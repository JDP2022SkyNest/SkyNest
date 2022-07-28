package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.response.BucketResponse;
import com.htecgroup.skynest.utils.tag.TagResponseUtil;

import java.util.Collections;
import java.util.UUID;

public class BucketResponseUtil {
  public static BucketResponse getCurrentPrivate() {
    return new BucketResponse(
        BucketEntityUtil.getPrivateBucket().getId(),
        BucketEntityUtil.getPrivateBucket().getCreatedBy().getId(),
        BucketEntityUtil.getPrivateBucket().getCreatedBy().getEmail(),
        "Name",
        BucketDtoUtil.companyDto.getId(),
        "Description",
        1000L,
        false,
        BucketEntityUtil.getPrivateBucket().getCreatedOn().toString(),
        Collections.singletonList(TagResponseUtil.get()));
  }

  public static BucketResponse getOtherPrivate() {
    BucketResponse bucketResponse = getCurrentPrivate();
    bucketResponse.setCreatedById(UUID.randomUUID());
    return bucketResponse;
  }
}
