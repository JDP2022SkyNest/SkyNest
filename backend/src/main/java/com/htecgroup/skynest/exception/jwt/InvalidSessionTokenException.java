package com.htecgroup.skynest.exception.jwt;

import org.springframework.http.HttpStatus;

public class InvalidSessionTokenException extends JwtException {

  private static final String MESSAGE = "Invalid session token";

  public InvalidSessionTokenException() {
    super(MESSAGE, HttpStatus.UNAUTHORIZED);
  }
}
