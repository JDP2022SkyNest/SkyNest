package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.request.FileInfoEditRequest;

public class FileEditRequestUtil {
  public static FileInfoEditRequest get() {
    return new FileInfoEditRequest("FileEditedName");
  }
}
