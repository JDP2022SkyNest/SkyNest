package com.htecgroup.skynest.exception.login;

import org.springframework.http.HttpStatus;

public class TooManyAttemptsException extends LoginException {

  private static final String MESSAGE = "Too many bad login requests, try again later";

  public TooManyAttemptsException() {
    super(MESSAGE, HttpStatus.TOO_MANY_REQUESTS);
  }
}
