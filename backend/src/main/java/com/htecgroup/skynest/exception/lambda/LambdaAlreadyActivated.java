package com.htecgroup.skynest.exception.lambda;

import org.springframework.http.HttpStatus;

public class LambdaAlreadyActivated extends LambdaException {

  public static final String MESSAGE = "Lambda is already activated for that bucket.";

  public LambdaAlreadyActivated() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
