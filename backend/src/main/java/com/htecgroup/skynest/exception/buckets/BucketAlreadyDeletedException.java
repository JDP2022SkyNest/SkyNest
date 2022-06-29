package com.htecgroup.skynest.exception.buckets;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class BucketAlreadyDeletedException extends SkyNestBaseException {

  private static final String MESSAGE = "Bucket already deleted";

  public BucketAlreadyDeletedException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
