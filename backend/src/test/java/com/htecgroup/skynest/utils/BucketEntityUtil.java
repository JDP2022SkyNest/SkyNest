package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.CompanyEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BucketEntityUtil {
  protected static final CompanyEntity companyEntity = new CompanyEntity();
  protected static String description = "Description";
  protected static Long size = 1000L;
  protected static boolean privateBucket = false;

  public static BucketEntity getPrivateBucket() {
    BucketEntity bucketEntity = new BucketEntity(companyEntity, description, size, privateBucket);
    bucketEntity.setName("Name");
    bucketEntity.setCreatedBy(UserEntityUtil.getVerified());
    return bucketEntity;
  }

  public static BucketEntity getOtherPrivateBucket() {
    BucketEntity bucketEntity = getPrivateBucket();
    UserEntity userEntity = UserEntityUtil.getVerified();
    userEntity.setId(UUID.randomUUID());
    bucketEntity.setCreatedBy(userEntity);
    return bucketEntity;
  }
}
