package com.htecgroup.skynest.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends CustomException {

  private AuthException(String message, HttpStatus status) {
    super(message, status);
  }

  public static final AuthException FORBIDDEN_FOR_WORKER =
      new AuthException("A worked can only view his/hers account details", HttpStatus.FORBIDDEN);
}
