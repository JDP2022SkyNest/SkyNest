package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.lambda.LambdaType;
import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.CompanyEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BucketEntityUtil {
  protected static final CompanyEntity companyEntity = new CompanyEntity();
  protected static String description = "Description";
  protected static Long size = 1000L;
  protected static boolean privateBucket = false;

  public static BucketEntity getPrivateBucket() {
    Set<LambdaType> lambdaTypeList = new HashSet<>();
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
    bucketEntity.setDeletedOn(LocalDateTime.now());
    return bucketEntity;
  }

  public static BucketEntity getBucketWithActivatedLambdas() {
    BucketEntity bucketEntity = getPrivateBucket();
    Set<LambdaType> lambdaTypes = new HashSet<>();
    lambdaTypes.add(LambdaType.UPLOAD_FILE_TO_EXTERNAL_SERVICE_LAMBDA);
    return bucketEntity.withLambdaTypes(lambdaTypes);
  }
}
