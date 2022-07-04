package com.htecgroup.skynest.exception.buckets;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class BucketException extends SkyNestBaseException {

  private static final long serialVersionUID = -1421880958860706858L;

  public BucketException(String message, HttpStatus status) {
    super(message, status);
  }
}
