package com.htecgroup.skynest.exception.auth;

import org.springframework.http.HttpStatus;

public class ForbiddenForWorkerException extends AuthException {

  private static final String MESSAGE = "A worked can only view his/hers account details";

  public ForbiddenForWorkerException() {
    super(MESSAGE, HttpStatus.FORBIDDEN);
  }
}
