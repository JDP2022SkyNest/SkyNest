package com.htecgroup.skynest.exception;

import org.springframework.http.HttpStatus;

public class RegisterException extends CustomException {

  private RegisterException(String message, HttpStatus status) {
    super(message, status);
  }

  public static final RegisterException EMAIL_IN_USE =
      new RegisterException("Email already in use", HttpStatus.CONFLICT);

  public static final RegisterException PHONE_NUMBER_IN_USE =
      new RegisterException("Phone number already in use", HttpStatus.CONFLICT);

  public static final RegisterException USER_ALREADY_VERIFIED =
      new RegisterException("User is already verified", HttpStatus.CONFLICT);
}
