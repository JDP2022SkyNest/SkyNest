package com.htecgroup.skynest.exception.buckets;

import org.springframework.http.HttpStatus;

public class BucketsTooFullException extends BucketException {

  private static final long serialVersionUID = 2205844449033213490L;
  private static final String MESSAGE = "Company/User buckets don't have enough space";

  public BucketsTooFullException() {
    super(MESSAGE, HttpStatus.PAYLOAD_TOO_LARGE);
  }
}
