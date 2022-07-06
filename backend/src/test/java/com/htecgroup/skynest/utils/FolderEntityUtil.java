package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.FolderEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FolderEntityUtil {

  protected static BucketEntity bucketEntityUtil = BucketEntityUtil.getPrivateBucket();

  public static FolderEntity getFolderWithoutParent() {
    return FolderEntity.builder().bucket(bucketEntityUtil).build();
  }

  public static FolderEntity getFolderWithParent() {
    return FolderEntity.builder()
        .parentFolder(getFolderWithoutParent())
        .bucket(bucketEntityUtil)
        .build();
  }

  public static FolderEntity getDeletedFolder() {
    FolderEntity folderEntity = new FolderEntity(null, bucketEntityUtil);
    folderEntity.setDeletedOn(LocalDateTime.now());
    return folderEntity;
  }
}
