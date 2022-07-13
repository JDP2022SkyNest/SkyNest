package com.htecgroup.skynest.exception.buckets;

import org.springframework.http.HttpStatus;

public class BucketNotFoundException extends BucketException {

  private static final long serialVersionUID = 7301498820089165907L;

  public static final String MESSAGE = "Bucket not found";

  public BucketNotFoundException() {
    super(MESSAGE, HttpStatus.NOT_FOUND);
  }
}
