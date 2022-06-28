package com.htecgroup.skynest.exception.auth;

import org.springframework.http.HttpStatus;

public class UserAlreadyEnabledException extends AuthException {

  public static final String MESSAGE = "User is already enabled";

  public UserAlreadyEnabledException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
