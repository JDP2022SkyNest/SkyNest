package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.folder.FolderAlreadyInsideBucketException;
import com.htecgroup.skynest.exception.folder.FolderAlreadyInsideFolderException;
import com.htecgroup.skynest.model.entity.FolderEntity;
import com.htecgroup.skynest.service.FolderValidatorService;
import org.springframework.stereotype.Service;

@Service
public class FolderValidatorServiceImpl implements FolderValidatorService {
  @Override
  public void checkIfFolderAlreadyInsideRoot(FolderEntity folderEntity) {
    if (folderEntity.getParentFolder() == null) {
      throw new FolderAlreadyInsideBucketException();
    }
  }

  @Override
  public void checkIfFolderAlreadyInsideFolder(
      FolderEntity folderEntity, FolderEntity parentFolderEntity) {
    if (folderEntity.getParentFolder() != null
        && folderEntity.getParentFolder().getId().equals(parentFolderEntity.getId())) {
      throw new FolderAlreadyInsideFolderException();
    }
  }
}
