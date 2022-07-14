package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.dto.BucketDto;
import com.htecgroup.skynest.model.dto.CompanyDto;
import com.htecgroup.skynest.model.dto.UserDto;

import java.time.LocalDateTime;
import java.util.UUID;

public class BucketDtoUtil {

  protected static final LocalDateTime currentDateTime = LocalDateTime.now();
  protected static final CompanyDto companyDto = new CompanyDto();
  protected static String name = "Name";
  protected static String description = "Description";
  protected static Long size = 1000L;
  protected static boolean privateBucket = false;

  protected static UserDto userDtoUtil = UserDtoUtil.getVerified();

  public static BucketDto getCurrentUsersDeletedBucket() {
    return new BucketDto(
        UUID.randomUUID(),
        currentDateTime,
        currentDateTime,
        currentDateTime,
        userDtoUtil,
        name,
        companyDto,
        description,
        size,
        privateBucket);
  }

  public static BucketDto getCurrentUsersNotDeletedBucket() {
    return new BucketDto(
        UUID.randomUUID(),
        currentDateTime,
        currentDateTime,
        null,
        userDtoUtil,
        name,
        companyDto,
        description,
        size,
        privateBucket);
  }

  public static BucketDto getOtherUsersNotDeletedBucket() {
    return new BucketDto(
        UUID.randomUUID(),
        currentDateTime,
        currentDateTime,
        null,
        UserDtoUtil.getOtherUser(),
        name,
        companyDto,
        description,
        size,
        privateBucket);
  }
}
