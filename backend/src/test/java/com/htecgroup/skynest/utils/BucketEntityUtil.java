package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.lambda.LambdaType;
import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.CompanyEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.utils.company.CompanyEntityUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BucketEntityUtil {
  protected static final CompanyEntity companyEntity = CompanyEntityUtil.get();
  protected static String description = "Description";
  protected static Long size = 1000L;
  protected static boolean privateBucket = false;

  public static BucketEntity getPrivateBucket() {
    List<LambdaType> lambdaTypeList = new ArrayList<>();
    BucketEntity bucketEntity =
        new BucketEntity(companyEntity, description, size, privateBucket, lambdaTypeList);
    bucketEntity.setId(UUID.fromString("2d0d675d-db5e-4729-9308-2e5c3e9d5007"));
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

  public static BucketEntity getDeletedBucket() {
    BucketEntity bucketEntity = getPrivateBucket();
    bucketEntity.delete();
    return bucketEntity;
  }
}
