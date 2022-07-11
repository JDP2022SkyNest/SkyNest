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

  public static BucketDto getDeletedBucket() {
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

  public static BucketDto getNotDeletedBucket() {
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

  public static BucketDto getOtherNotDeletedBucket() {
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
}
