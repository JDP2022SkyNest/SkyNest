package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.request.MoveFolderToBucketRequest;

import java.util.UUID;

public class MoveFolderToBucketRequestUtil {

  public static MoveFolderToBucketRequest get() {
    return new MoveFolderToBucketRequest(UUID.randomUUID());
  }
}
