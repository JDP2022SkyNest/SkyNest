package com.htecgroup.skynest.exception;

import org.springframework.http.HttpStatus;

public class JwtException extends CustomException {

  private JwtException(String message, HttpStatus status) {
    super(message, status);
  }

  public static final JwtException INVALID_SESSION_TOKEN =
      new JwtException("Invalid session token", HttpStatus.UNAUTHORIZED);

  public static final JwtException INVALID_ALGORITHM =
      new JwtException("Server error, invalid algorithm", HttpStatus.INTERNAL_SERVER_ERROR);

  public static final JwtException INVALID_EMAIL_TOKEN =
      new JwtException("Invalid email token", HttpStatus.UNAUTHORIZED);
}
