package com.htecgroup.skynest.exception.lambda;

import org.springframework.http.HttpStatus;

public class DropboxFailedException extends LambdaException {

  private static final String MESSAGE = "There was an issue with the dropbox file upload lambda";

  public DropboxFailedException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
