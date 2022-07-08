package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.dto.BucketDto;
import com.htecgroup.skynest.model.dto.FolderDto;
import com.htecgroup.skynest.model.entity.BucketEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class FolderDtoUtil {

  protected static final LocalDateTime currentDateTime = LocalDateTime.now();
  protected static BucketEntity bucketEntityUtil = BucketEntityUtil.getPrivateBucket();

  protected static BucketDto bucketDtoUtil = BucketDtoUtil.getNotDeletedBucket();

  protected static String name = "FolderName";

  public static FolderDto getFolderWithoutParent() {
    FolderDto folderDto =
        new FolderDto(
            UUID.randomUUID(),
            currentDateTime,
            currentDateTime,
            null,
            UserDtoUtil.getVerified(),
            name,
            null,
            bucketDtoUtil);
    return folderDto;
  }

  public static FolderDto getFolderWithParent() {
    FolderDto folderDto =
        new FolderDto(
            UUID.randomUUID(),
            currentDateTime,
            currentDateTime,
            null,
            UserDtoUtil.getVerified(),
            name,
            FolderDtoUtil.getFolderWithoutParent(),
            bucketDtoUtil);
    return folderDto;
  }
}
