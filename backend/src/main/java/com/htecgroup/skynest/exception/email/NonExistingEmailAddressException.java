package com.htecgroup.skynest.exception.email;

import org.springframework.http.HttpStatus;

public class NonExistingEmailAddressException extends EmailException {

  private static final String MESSAGE = "The given email address doesn't exist";

  public NonExistingEmailAddressException() {
    super(MESSAGE, HttpStatus.BAD_REQUEST);
  }
}
