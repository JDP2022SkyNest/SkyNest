package com.htecgroup.skynest.exception.login;

import org.springframework.http.HttpStatus;

public class WrongPasswordException extends LoginException {

  private static final String MESSAGE = "Wrong password";

  public WrongPasswordException() {
    super(MESSAGE, HttpStatus.UNAUTHORIZED);
  }
}
