package com.htecgroup.skynest.exception.buckets;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class BucketNotFoundException extends SkyNestBaseException {

  private static final String MESSAGE = "Bucket not found";

  public BucketNotFoundException() {
    super(MESSAGE, HttpStatus.NOT_FOUND);
  }
}
