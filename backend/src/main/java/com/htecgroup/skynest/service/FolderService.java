package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.response.FolderResponse;

import java.util.UUID;

public interface FolderService {

  FolderResponse getFolder(UUID uuid);
}
