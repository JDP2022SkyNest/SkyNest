package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.request.BucketEditRequest;

public class BucketEditRequestUtil {
  public static BucketEditRequest get() {
    return new BucketEditRequest("NewName", "NewDescription", String.valueOf(false));
  }
}
