package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.FolderEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FolderEntityUtil {

  protected static BucketEntity bucketEntityUtil = BucketEntityUtil.getPrivateBucket();

  public static FolderEntity getFolderWithoutParent() {
    FolderEntity folderEntity = new FolderEntity(null, bucketEntityUtil);
    folderEntity.setName("FolderName");
    folderEntity.setCreatedBy(UserEntityUtil.getVerified());
    return folderEntity;
  }

  public static FolderEntity getFolderWithParent() {
    FolderEntity folderEntity = new FolderEntity(getFolderWithoutParent(), bucketEntityUtil);
    folderEntity.setName("FolderName");
    folderEntity.setCreatedBy(UserEntityUtil.getVerified());
    return folderEntity;
  }
}
