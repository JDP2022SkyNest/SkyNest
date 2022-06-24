package com.htecgroup.skynest.exception.register;

import org.springframework.http.HttpStatus;

public class UserAlreadyVerifiedException extends RegisterException {

  private static final String MESSAGE = "User is already verified";

  public UserAlreadyVerifiedException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
