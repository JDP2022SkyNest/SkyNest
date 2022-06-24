package com.htecgroup.skynest.exception.register;

import org.springframework.http.HttpStatus;

public class EmailAlreadyInUseException extends RegisterException {

  private static final String MESSAGE = "Email already in use";

  public EmailAlreadyInUseException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
