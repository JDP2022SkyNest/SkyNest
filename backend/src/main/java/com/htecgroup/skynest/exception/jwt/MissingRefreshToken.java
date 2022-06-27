package com.htecgroup.skynest.exception.jwt;

import org.springframework.http.HttpStatus;

public class MissingRefreshToken extends JwtException {

  private static final String MESSAGE = "Refresh Token is missing";

  public MissingRefreshToken() {
    super(MESSAGE, HttpStatus.UNAUTHORIZED);
  }
}
