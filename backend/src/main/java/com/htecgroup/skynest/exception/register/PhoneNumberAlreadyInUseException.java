package com.htecgroup.skynest.exception.register;

import org.springframework.http.HttpStatus;

public class PhoneNumberAlreadyInUseException extends RegisterException {

  private static final String MESSAGE = "Phone number already in use";

  public PhoneNumberAlreadyInUseException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
