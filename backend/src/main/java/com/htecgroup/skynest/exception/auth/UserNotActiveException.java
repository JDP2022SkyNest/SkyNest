package com.htecgroup.skynest.exception.auth;

import org.springframework.http.HttpStatus;

public class UserNotActiveException extends AuthException {

  public static final String MESSAGE = "User is not active(Disabled/Deleted)";

  public UserNotActiveException() {
    super(MESSAGE, HttpStatus.FORBIDDEN);
  }
}
