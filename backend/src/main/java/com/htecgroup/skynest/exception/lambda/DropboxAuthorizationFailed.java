package com.htecgroup.skynest.exception.lambda;

import org.springframework.http.HttpStatus;

public class DropboxAuthorizationFailed extends LambdaException {

  private static final String MESSAGE = "There was an issue with the dropbox code";

  public DropboxAuthorizationFailed() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
