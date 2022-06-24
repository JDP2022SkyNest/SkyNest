package com.htecgroup.skynest.exception.login;

import org.springframework.http.HttpStatus;

public class IOErrorException extends LoginException {

  private static final String MESSAGE = "IO error during login";

  public IOErrorException() {
    super(MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
