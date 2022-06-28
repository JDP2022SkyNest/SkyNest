package com.htecgroup.skynest.exception.auth;

import org.springframework.http.HttpStatus;

public class UserNotVerifiedException extends AuthException {

  public static final String MESSAGE = "An unverified user can't be enabled.";

  public UserNotVerifiedException() {
    super(MESSAGE, HttpStatus.FORBIDDEN);
  }
}
