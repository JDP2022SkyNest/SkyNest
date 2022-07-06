package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.response.FolderResponse;

import java.util.List;
import java.util.UUID;

public interface FolderService {

  FolderResponse getFolderDetails(UUID uuid);

  List<FolderResponse> getAllRootFolders(UUID bucketId);

  List<FolderResponse> getAllFoldersFromParent(UUID parentFolderId);
}
