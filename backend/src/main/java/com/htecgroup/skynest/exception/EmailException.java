package com.htecgroup.skynest.exception;

import org.springframework.http.HttpStatus;

public class EmailException extends CustomException {
  private EmailException(String message, HttpStatus status) {
    super(message, status);
  }

  public static final EmailException NON_EXISTING_EMAIL_ADDRESS =
      new EmailException("The given email address doesn't exist", HttpStatus.BAD_REQUEST);

  public static final EmailException SENDING_EMAIL_FAILED =
      new EmailException("Server failed sending the email", HttpStatus.INTERNAL_SERVER_ERROR);
}
