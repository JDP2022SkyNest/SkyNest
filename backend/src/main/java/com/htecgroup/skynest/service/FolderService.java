package com.htecgroup.skynest.service;

import com.htecgroup.skynest.annotation.ParentFolderIsInTheSameBucket;
import com.htecgroup.skynest.model.request.FolderCreateRequest;
import com.htecgroup.skynest.model.request.FolderEditRequest;
import com.htecgroup.skynest.model.response.FolderResponse;
import com.htecgroup.skynest.model.response.StorageContentResponse;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface FolderService {
  void removeFolder(UUID uuid);

  FolderResponse restoreFolder(UUID folderId);

  FolderResponse createFolder(
      @Valid @ParentFolderIsInTheSameBucket FolderCreateRequest folderCreateRequest);

  FolderResponse getFolderDetails(UUID uuid);

  FolderResponse editFolder(FolderEditRequest folderEditRequest, UUID folderId);

  List<FolderResponse> getAllRootFolders(UUID bucketId);

  List<FolderResponse> getAllFoldersWithParent(UUID parentFolderId);

  void moveFolderToRoot(UUID folderId);

  void moveFolderToFolder(UUID folderId, UUID destinationFolderId);

  StorageContentResponse getFolderContent(UUID folderId);

  List<FolderResponse> getAllFoldersWithTag(UUID tagId, UUID loggedUserId);
}
