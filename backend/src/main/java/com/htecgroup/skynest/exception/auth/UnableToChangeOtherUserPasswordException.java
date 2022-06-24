package com.htecgroup.skynest.exception.auth;

import org.springframework.http.HttpStatus;

public class UnableToChangeOtherUserPasswordException extends AuthException {

  private static final String MESSAGE = "Can't change password to user other then yourself";

  public UnableToChangeOtherUserPasswordException() {
    super(MESSAGE, HttpStatus.FORBIDDEN);
  }
}
