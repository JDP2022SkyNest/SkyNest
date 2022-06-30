package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.dto.RoleDto;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.entity.RoleEntity;
import com.htecgroup.skynest.utils.company.CompanyDtoUtil;

import java.time.LocalDateTime;
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

  public static UserDto getVerifiedManager() {
    return getVerified().withRole(new RoleDto(UUID.randomUUID(), RoleEntity.ROLE_MANAGER));
  }

  public static UserDto getVerifiedButDisabledUser() {
    return getNotVerified().withVerified(true).withDeletedOn(LocalDateTime.now());
  }

  public static UserDto getUserWithCompany() {
    return getVerified().withCompany(CompanyDtoUtil.getCompanyDto());
  }
}
