package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.entity.UserObjectAccessKey;

import java.util.UUID;

public class UserObjectAccessKeyUtil {

  public static final UserObjectAccessKey get() {
    return new UserObjectAccessKey(UUID.randomUUID(), BucketEntityUtil.getPrivateBucket().getId());
  }
}
