package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.entity.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserObjectAccessEntityUtil {
  protected static UserEntity userEntity = UserEntityUtil.getVerified();
  protected static BucketEntity bucketEntityUtil = BucketEntityUtil.getPrivateBucket();
  protected static LocalDateTime localDateTime = LocalDateTime.now();

  protected static AccessTypeEntity accessType = AccessTypeEntityUtil.get(AccessType.OWNER);

  protected static UserObjectAccessKey userObjectAccessKey = UserObjectAccessKeyUtil.get();

  public static UserObjectAccessEntity getUserObjectAccess() {
    UserObjectAccessEntity userObjectAccess =
        new UserObjectAccessEntity(
            userObjectAccessKey,
            userEntity,
            bucketEntityUtil,
            localDateTime,
            accessType,
            userEntity);
    return userObjectAccess;
  }
}
