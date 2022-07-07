package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.FolderEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FolderEntityUtil {

  protected static BucketEntity bucketEntityUtil = BucketEntityUtil.getPrivateBucket();

  public static FolderEntity getFolderWithoutParent() {
    FolderEntity folderEntity = new FolderEntity(null, bucketEntityUtil);
    folderEntity.setName("FolderName");
    folderEntity.setCreatedBy(UserEntityUtil.getVerified());
    folderEntity.setId(UUID.randomUUID());
    return folderEntity;
  }

  public static FolderEntity getFolderWithParent() {
    FolderEntity folderEntity = new FolderEntity(getFolderWithoutParent(), bucketEntityUtil);
    folderEntity.setName("FolderName");
    folderEntity.setCreatedBy(UserEntityUtil.getVerified());
    return folderEntity;
  }

  public static FolderEntity getDeletedFolder() {
    FolderEntity folderEntity = new FolderEntity(null, bucketEntityUtil);
    folderEntity.setDeletedOn(LocalDateTime.now());
    return folderEntity;
  }
}
