package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.FolderEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FolderEntityUtil {

  protected static BucketEntity bucketEntityUtil = BucketEntityUtil.getPrivateBucket();

  public static FolderEntity getFolderWithoutParent() {
    FolderEntity folderEntity = new FolderEntity(null, bucketEntityUtil);
    folderEntity.setId(UUID.fromString("18b098d6-6861-40a7-8747-df3d5fe1d0d4"));
    folderEntity.setName("FolderName");
    folderEntity.setCreatedBy(UserEntityUtil.getVerified());
    return folderEntity;
  }

  public static FolderEntity getFolderWithParent() {
    FolderEntity folderEntity = new FolderEntity(getFolderWithoutParent(), bucketEntityUtil);
    folderEntity.setId(UUID.fromString("63b1b474-8be0-4440-82af-46c9036bb204"));
    folderEntity.setName("FolderName");
    folderEntity.setCreatedBy(UserEntityUtil.getVerified());
    return folderEntity;
  }

  public static FolderEntity getDeletedFolder() {
    FolderEntity folderEntity = new FolderEntity(null, bucketEntityUtil);
    folderEntity.delete();
    return folderEntity;
  }
}
