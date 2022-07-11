package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.request.MoveFolderToBucketRequest;
import com.htecgroup.skynest.model.request.MoveFolderToFolderRequest;

import java.util.UUID;

public class MoveFolderRequestUtil {

  public static MoveFolderToBucketRequest getToBucket() {
    return new MoveFolderToBucketRequest(UUID.randomUUID());
  }

  public static MoveFolderToFolderRequest getToFolder() {
    return new MoveFolderToFolderRequest(UUID.randomUUID());
  }
}
