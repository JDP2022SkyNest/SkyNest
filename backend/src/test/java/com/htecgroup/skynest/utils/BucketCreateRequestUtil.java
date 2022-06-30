package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.request.BucketCreateRequest;

public class BucketCreateRequestUtil {
  public static BucketCreateRequest get() {
    return new BucketCreateRequest("Name", "Description");
  }
}
