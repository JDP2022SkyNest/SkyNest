package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.entity.BucketEntity;

public class BucketEntityUtil extends BucketBasicUtil {

  public static BucketEntity getNotPublic() {
    return new BucketEntity(companyEntity, description, size, isPublic);
  }
}
