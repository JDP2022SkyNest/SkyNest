package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.entity.CompanyEntity;

import java.time.LocalDateTime;

public class BucketBasicUtil {
  protected static final LocalDateTime currentDateTime = LocalDateTime.now();
  protected static final UserEntityUtil userEntity = new UserEntityUtil();
  protected static final CompanyEntity companyEntity = new CompanyEntity();
  protected static String name = "Name";
  protected static String description = "Description";
  protected static long size = 100;
  protected static boolean isPublic = false;
}
