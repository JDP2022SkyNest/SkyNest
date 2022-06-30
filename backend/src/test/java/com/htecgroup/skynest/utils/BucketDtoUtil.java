package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.dto.BucketDto;
import com.htecgroup.skynest.model.entity.CompanyEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class BucketDtoUtil {

  protected static final LocalDateTime currentDateTime = LocalDateTime.now();
  protected static final CompanyEntity companyEntity = new CompanyEntity();
  protected static String name = "Name";
  protected static String description = "Description";
  protected static Long size = 1000L;
  protected static boolean privateBucket = false;

  public static BucketDto getDeletedBucket() {
    return new BucketDto(
        UUID.randomUUID(),
        currentDateTime,
        currentDateTime,
        currentDateTime,
        BucketEntityUtil.getPrivateBucket().getCreatedBy(),
        name,
        companyEntity,
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
        BucketEntityUtil.getPrivateBucket().getCreatedBy(),
        name,
        companyEntity,
        description,
        size,
        privateBucket);
  }
}
