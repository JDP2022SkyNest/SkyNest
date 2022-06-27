package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.response.UserResponse;

import java.util.UUID;

public class UserResponseUtil extends BasicUtil {

  public static UserResponse getWorker() {
    return new UserResponse(
        UUID.randomUUID().toString(),
        email,
        name,
        surname,
        phoneNumber,
        address,
        roleWorkerEntity.getName());
  }

  public static UserResponse getAdmin() {
    UserResponse userResponse = getWorker();
    userResponse.setRoleName(roleAdminEntity.getName());
    return userResponse;
  }
}
