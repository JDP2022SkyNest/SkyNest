package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.entity.AccessType;
import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.model.request.PermissionGrantRequest;

public class PermissionGrantRequestUtil {

  private static final UserEntity user = UserEntityUtil.getVerified();
  private static final BucketEntity bucket = BucketEntityUtil.getPrivateBucket();

  public static PermissionGrantRequest get(AccessType accessType) {
    return new PermissionGrantRequest(user.getEmail(), bucket.getId(), accessType.getText());
  }
}
