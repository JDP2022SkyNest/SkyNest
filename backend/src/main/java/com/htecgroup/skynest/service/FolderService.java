package com.htecgroup.skynest.service;

import com.htecgroup.skynest.annotation.ParentFolderIsInTheSameBucket;
import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.FolderEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.model.request.FolderCreateRequest;
import com.htecgroup.skynest.model.response.FolderResponse;

import javax.validation.Valid;

public interface FolderService {
  FolderResponse createFolder(
      @Valid @ParentFolderIsInTheSameBucket FolderCreateRequest folderCreateRequest);

  FolderEntity setNewFolder(
      FolderEntity folderEntity,
      UserEntity currentUser,
      BucketEntity bucketEntity,
      FolderEntity parentFolderEntity);
}
