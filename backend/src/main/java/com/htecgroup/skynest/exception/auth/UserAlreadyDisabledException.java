package com.htecgroup.skynest.exception.auth;

import org.springframework.http.HttpStatus;

public class UserAlreadyDisabledException extends AuthException {

  public static final String MESSAGE = "User is already disabled.";

  public UserAlreadyDisabledException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
