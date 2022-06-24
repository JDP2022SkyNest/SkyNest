package com.htecgroup.skynest.exception.auth;

import org.springframework.http.HttpStatus;

public class UserAlreadyEnabledException extends AuthException {

  private static final String MESSAGE = "User is already enabled";

  public UserAlreadyEnabledException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
