package com.htecgroup.skynest.exception.jwt;

import org.springframework.http.HttpStatus;

public class InvalidEmailTokenException extends JwtException {

  private static final String MESSAGE = "Invalid email token";

  public InvalidEmailTokenException() {
    super(MESSAGE, HttpStatus.UNAUTHORIZED);
  }
}
