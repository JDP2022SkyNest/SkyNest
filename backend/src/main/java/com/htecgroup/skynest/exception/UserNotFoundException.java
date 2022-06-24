package com.htecgroup.skynest.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends SkyNestBaseException {

  private static final String MESSAGE = "User not found";

  public UserNotFoundException() {
    super(MESSAGE, HttpStatus.NOT_FOUND);
  }
}
