package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.response.FolderResponse;

import java.util.UUID;

public interface FolderService {
  void removeFolder(UUID uuid);

  FolderResponse getFolderDetails(UUID uuid);
}
