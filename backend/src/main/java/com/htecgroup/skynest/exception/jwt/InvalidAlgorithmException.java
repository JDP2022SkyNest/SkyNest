package com.htecgroup.skynest.exception.jwt;

import org.springframework.http.HttpStatus;

public class InvalidAlgorithmException extends JwtException {

  private static final String MESSAGE = "Server error, invalid algorithm";

  public InvalidAlgorithmException() {
    super(MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
