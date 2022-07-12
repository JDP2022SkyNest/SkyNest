package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.request.FolderEditRequest;

public class FolderEditRequestUtil {
  public static FolderEditRequest get() {
    return new FolderEditRequest("FolderEditedName");
  }
}
