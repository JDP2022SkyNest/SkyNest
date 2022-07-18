package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.entity.UserObjectAccessKey;

import java.util.UUID;

public class UserObjectAccessKeyUtil {

  public static final UserObjectAccessKey get() {
    return new UserObjectAccessKey(
        (UUID.fromString("24c1b474-96e0-2240-97af-87c9036bb756")),
        BucketEntityUtil.getPrivateBucket().getId());
  }
}
