package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.dto.UserDto;

import java.util.UUID;

public class UserDtoUtil extends BasicUtil {

  public static UserDto getNotVerified() {
    return new UserDto(
        UUID.randomUUID(),
        currentDateTime,
        currentDateTime,
        null,
        email,
        password,
        encryptedPassoword,
        name,
        surname,
        address,
        phoneNumber,
        false,
        false,
        roleWorkerDto,
        null);
  }

  public static UserDto getVerified() {
    return getNotVerified().withVerified(true).withEnabled(true);
  }
}
