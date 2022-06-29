package com.htecgroup.skynest.exception.buckets;

import org.springframework.http.HttpStatus;

public class BucketAccessDeniedException extends BucketException {

  private static final long serialVersionUID = 1076489718318876620L;

  private static final String MESSAGE = "User does not have access to bucket";

  public BucketAccessDeniedException() {
    super(MESSAGE, HttpStatus.FORBIDDEN);
  }
}
