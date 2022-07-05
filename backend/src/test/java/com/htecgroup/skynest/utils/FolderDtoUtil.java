package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.dto.FolderDto;
import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class FolderDtoUtil {
  protected static final LocalDateTime currentDateTime = LocalDateTime.now();
  protected static BucketEntity bucketEntityUtil = BucketEntityUtil.getPrivateBucket();

  protected static UserEntity userEntityUtil = UserEntityUtil.getVerified();

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
            .bucket(bucketEntityUtil)
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
            .parentFolder(FolderEntityUtil.getFolderWithoutParent())
            .bucket(bucketEntityUtil)
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
            .parentFolder(FolderEntityUtil.getFolderWithoutParent())
            .bucket(bucketEntityUtil)
            .build();
    return folderDto;
  }
}
