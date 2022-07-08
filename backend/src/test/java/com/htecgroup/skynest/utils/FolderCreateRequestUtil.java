package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.request.FolderCreateRequest;

import java.util.UUID;

public class FolderCreateRequestUtil {
  public static FolderCreateRequest get() {
    return new FolderCreateRequest("FolderName", UUID.randomUUID(), UUID.randomUUID());
  }
}
