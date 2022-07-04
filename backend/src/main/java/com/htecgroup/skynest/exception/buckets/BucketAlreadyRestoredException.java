package com.htecgroup.skynest.exception.buckets;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class BucketAlreadyRestoredException extends SkyNestBaseException {
  public static final String MESSAGE = "Bucket already restored";

  public BucketAlreadyRestoredException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
