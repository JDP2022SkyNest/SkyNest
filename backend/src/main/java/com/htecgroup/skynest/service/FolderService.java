package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.dto.FolderDto;
import com.htecgroup.skynest.model.request.FolderCreateRequest;
import com.htecgroup.skynest.model.response.FolderResponse;

public interface FolderService {
  FolderResponse createFolder(FolderCreateRequest folderCreateRequest);

  FolderDto findFolderByName(String name);
}
