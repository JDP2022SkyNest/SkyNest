package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.entity.AccessType;
import com.htecgroup.skynest.model.entity.AccessTypeEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.model.request.PermissionEditRequest;

public class PermissionEditRequestUtil {

  protected static UserEntity user = UserEntityUtil.getVerified();
  protected static AccessTypeEntity accessType = AccessTypeEntityUtil.get(AccessType.EDIT);

  public static PermissionEditRequest get() {
    return new PermissionEditRequest(user.getEmail(), accessType.toString());
  }
}
