package com.htecgroup.skynest.service;

import com.htecgroup.skynest.annotation.ParentFolderIsInTheSameBucket;
import com.htecgroup.skynest.model.request.FolderCreateRequest;
import com.htecgroup.skynest.model.request.MoveFolderToBucketRequest;
import com.htecgroup.skynest.model.request.MoveFolderToFolderRequest;
import com.htecgroup.skynest.model.response.FolderResponse;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface FolderService {
  void removeFolder(UUID uuid);

  FolderResponse createFolder(
      @Valid @ParentFolderIsInTheSameBucket FolderCreateRequest folderCreateRequest);

  FolderResponse getFolderDetails(UUID uuid);

  List<FolderResponse> getAllRootFolders(UUID bucketId);

  List<FolderResponse> getAllFoldersWithParent(UUID parentFolderId);

  void moveFolderToBucket(MoveFolderToBucketRequest moveFolderToBucketRequest, UUID uuid);

  void moveFolderToFolder(MoveFolderToFolderRequest moveFolderRequest, UUID uuid);
}
