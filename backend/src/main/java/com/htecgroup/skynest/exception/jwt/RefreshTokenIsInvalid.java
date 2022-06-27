package com.htecgroup.skynest.exception.jwt;

import org.springframework.http.HttpStatus;

public class RefreshTokenIsInvalid extends JwtException {

  private static final String MESSAGE = "Refresh Token is invalid or expired";

  public RefreshTokenIsInvalid() {
    super(MESSAGE, HttpStatus.UNAUTHORIZED);
  }
}
