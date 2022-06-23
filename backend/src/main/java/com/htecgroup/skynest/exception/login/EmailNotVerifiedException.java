package com.htecgroup.skynest.exception.login;

import org.springframework.http.HttpStatus;

public class EmailNotVerifiedException extends LoginException {

  private static final String MESSAGE = "Email not verified";

  public EmailNotVerifiedException() {
    super(MESSAGE, HttpStatus.FORBIDDEN);
  }
}
