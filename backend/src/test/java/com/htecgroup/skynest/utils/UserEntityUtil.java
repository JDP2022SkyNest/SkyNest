package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.utils.company.CompanyEntityUtil;

import java.util.UUID;

public class UserEntityUtil extends BasicUtil {

  public static UserEntity getNotVerified() {
    return new UserEntity(
        UUID.fromString("55ff7452-5513-47f3-be82-59c34cb80140"),
        currentDateTime,
        currentDateTime,
        null,
        email,
        encryptedPassoword,
        name,
        surname,
        address,
        phoneNumber,
        null,
        false,
        false,
        roleWorkerEntity,
        CompanyEntityUtil.get());
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
