package com.htecgroup.skynest.exception;

import org.springframework.http.HttpStatus;

public class WrongOldPasswordException extends SkyNestBaseException {

  private static final String MESSAGE = "Incorrect old password";

  public WrongOldPasswordException() {
    super(MESSAGE, HttpStatus.BAD_REQUEST);
  }
}
