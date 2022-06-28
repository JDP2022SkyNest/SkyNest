package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.entity.BucketEntity;

public class BucketEntityUtil extends BucketBasicUtil {

  public static BucketEntity getPrivateBucket() {
    return new BucketEntity(companyEntity, description, size, privateBucket);
  }
}
