package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.entity.FileMetadataEntity;
import com.htecgroup.skynest.model.response.FileResponse;

public class FileResponseUtil {

  private static FileMetadataEntity rootFile =
      FileMetadataEntityUtil.getNotRootFileMetadataEntity();
  private static FileMetadataEntity fileWithParent =
      FileMetadataEntityUtil.getNotRootFileMetadataEntity();

  public static FileResponse getRootFile() {
    return new FileResponse(
        rootFile.getId(),
        rootFile.getCreatedOn() == null ? null : rootFile.getCreatedOn().toString(),
        rootFile.getModifiedOn() == null ? null : rootFile.getCreatedOn().toString(),
        !rootFile.isDeleted() ? null : rootFile.getCreatedOn().toString(),
        rootFile.getName(),
        rootFile.getCreatedBy().getId(),
        rootFile.getParentFolder().getId() == null ? null : rootFile.getParentFolder().getId(),
        rootFile.getBucket().getId(),
        rootFile.getType(),
        Long.toString(rootFile.getSize()));
  }

  public static FileResponse getFileWithParent() {
    return new FileResponse(
        fileWithParent.getId(),
        fileWithParent.getCreatedOn() == null ? null : fileWithParent.getCreatedOn().toString(),
        fileWithParent.getModifiedOn() == null ? null : fileWithParent.getCreatedOn().toString(),
        !fileWithParent.isDeleted() ? null : fileWithParent.getCreatedOn().toString(),
        fileWithParent.getName(),
        fileWithParent.getCreatedBy().getId(),
        fileWithParent.getParentFolder().getId(),
        fileWithParent.getBucket().getId(),
        fileWithParent.getType(),
        Long.toString(fileWithParent.getSize()));
  }
}
