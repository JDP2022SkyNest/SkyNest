package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.entity.FolderEntity;
import com.htecgroup.skynest.model.response.FolderResponse;
import com.htecgroup.skynest.utils.tag.TagResponseUtil;

import java.util.Collections;

public class FolderResponseUtil {

  private static FolderEntity rootFolder = FolderEntityUtil.getFolderWithoutParent();
  private static FolderEntity folderWithParent = FolderEntityUtil.getFolderWithParent();

  public static FolderResponse getRootFolder() {
    return new FolderResponse(
        rootFolder.getId(),
        rootFolder.getCreatedOn() == null ? null : getRootFolder().getCreatedOn().toString(),
        rootFolder.getModifiedOn() == null ? null : getRootFolder().getModifiedOn().toString(),
        !rootFolder.isDeleted() ? null : getRootFolder().getDeletedOn().toString(),
        rootFolder.getName(),
        rootFolder.getCreatedBy().getId(),
        rootFolder.getCreatedBy().getEmail(),
        rootFolder.getParentFolder() == null ? null : rootFolder.getParentFolder().getId(),
        rootFolder.getBucket().getId(),
        Collections.singletonList(TagResponseUtil.get()));
  }

  public static FolderResponse getFolderWithParent() {
    return new FolderResponse(
        folderWithParent.getId(),
        folderWithParent.getCreatedOn() == null ? null : folderWithParent.getCreatedOn().toString(),
        folderWithParent.getModifiedOn() == null
            ? null
            : getRootFolder().getModifiedOn().toString(),
        !folderWithParent.isDeleted() ? null : getRootFolder().getDeletedOn().toString(),
        folderWithParent.getName(),
        folderWithParent.getCreatedBy().getId(),
        folderWithParent.getCreatedBy().getEmail(),
        folderWithParent.getParentFolder().getId(),
        folderWithParent.getBucket().getId(),
        Collections.singletonList(TagResponseUtil.get()));
  }
}
