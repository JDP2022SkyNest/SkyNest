package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.dto.BucketDto;
import com.htecgroup.skynest.model.dto.FolderDto;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.entity.BucketEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class FolderDtoUtil {
  protected static final LocalDateTime currentDateTime = LocalDateTime.now();
  protected static BucketEntity bucketEntityUtil = BucketEntityUtil.getPrivateBucket();

  protected static UserDto userEntityUtil = UserDtoUtil.getVerified();

  protected static BucketDto bucketDtoUtil = BucketDtoUtil.getCurrentUsersNotDeletedBucket();

  protected static String name = "FolderName";

  public static FolderDto getFolderWithoutParent() {
    FolderDto folderDto =
        FolderDto.builder()
            .id(UUID.randomUUID())
            .createdOn(LocalDateTime.now())
            .modifiedOn(LocalDateTime.now())
            .deletedOn(null)
            .createdBy(userEntityUtil)
            .name("FolderName")
            .parentFolder(null)
            .bucket(bucketDtoUtil)
            .build();
    return folderDto;
  }

  public static FolderDto getFolderWithParent() {
    FolderDto folderDto =
        FolderDto.builder()
            .id(UUID.randomUUID())
            .createdOn(LocalDateTime.now())
            .modifiedOn(LocalDateTime.now())
            .deletedOn(null)
            .createdBy(userEntityUtil)
            .name("FolderName")
            .parentFolder(getFolderWithoutParent())
            .bucket(bucketDtoUtil)
            .build();
    return folderDto;
  }

  public static FolderDto getDeletedFolder() {
    FolderDto folderDto =
        FolderDto.builder()
            .id(UUID.randomUUID())
            .createdOn(LocalDateTime.now())
            .modifiedOn(LocalDateTime.now())
            .deletedOn(LocalDateTime.now())
            .createdBy(userEntityUtil)
            .name("FolderName")
            .parentFolder(getFolderWithoutParent())
            .bucket(bucketDtoUtil)
            .build();
    return folderDto;
  }
}
