package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.entity.FolderEntity;

public interface FolderValidatorService {

  void checkIfFolderAlreadyInsideRoot(FolderEntity folderEntity);

  void checkIfFolderAlreadyInsideFolder(FolderEntity folderEntity, FolderEntity parentFolderEntity);
}
