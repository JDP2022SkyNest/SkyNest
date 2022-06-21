package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.entity.UserEntity;

import java.util.UUID;

public class UserEntityUtil extends BasicUtil {

  public static UserEntity getNotVerified() {
    return new UserEntity(
        UUID.randomUUID(),
        currentDateTime,
        currentDateTime,
        null,
        email,
        encryptedPassoword,
        name,
        surname,
        address,
        phoneNumber,
        false,
        false,
        roleWorkerEntity,
        null);
  }

  public static UserEntity getVerified() {
    return getNotVerified().withVerified(true).withEnabled(true);
  }

  public static UserEntity getAdmin() {
    UserEntity userEntity = getNotVerified();
    userEntity.setRole(roleAdminEntity);
    return userEntity;
  }
}
