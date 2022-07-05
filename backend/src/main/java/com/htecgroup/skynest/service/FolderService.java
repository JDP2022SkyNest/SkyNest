package com.htecgroup.skynest.service;

import com.htecgroup.skynest.annotation.ParentFolderIsInTheSameBucket;
import com.htecgroup.skynest.model.entity.FolderEntity;
import com.htecgroup.skynest.model.request.FolderCreateRequest;
import com.htecgroup.skynest.model.response.FolderResponse;

import javax.validation.Valid;
import java.util.UUID;

public interface FolderService {
  FolderResponse createFolder(
      @Valid @ParentFolderIsInTheSameBucket FolderCreateRequest folderCreateRequest);

  FolderEntity setNewFolder(
      FolderEntity folderEntity,
      UUID currentUserId,
      UUID bucketEntityId,
      UUID parentFolderEntityId);

  FolderResponse getFolderDetails(UUID uuid);
}
