package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.request.FolderCreateRequest;

public class FolderCreateRequestUtil {
  public static FolderCreateRequest get() {
    return new FolderCreateRequest("FolderName", "Name", "ParentFolderName");
  }
}
