package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.dto.FolderDto;
import com.htecgroup.skynest.model.request.FolderCreateRequest;
import com.htecgroup.skynest.model.response.FolderResponse;

import java.util.UUID;

public interface FolderService {
  FolderResponse createFolder(FolderCreateRequest folderCreateRequest);

  FolderDto findFolderByName(String name);

  FolderDto findFolderById(UUID uuid);
}
