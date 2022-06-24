package com.htecgroup.skynest.exception.email;

import org.springframework.http.HttpStatus;

public class SendingEmailFailedException extends EmailException {

  private static final String MESSAGE = "Server failed sending the email";

  public SendingEmailFailedException() {
    super(MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
