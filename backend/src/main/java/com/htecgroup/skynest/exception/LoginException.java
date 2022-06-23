package com.htecgroup.skynest.exception;

import org.springframework.http.HttpStatus;

public class LoginException extends CustomException {

  private LoginException(String message, HttpStatus status) {
    super(message, status);
  }

  public static final LoginException EMAIL_NOT_VERIFIED =
      new LoginException("Email not verified", HttpStatus.FORBIDDEN);

  public static final LoginException WRONG_PASSWORD =
      new LoginException("Wrong password", HttpStatus.UNAUTHORIZED);

  public static final LoginException TOO_MANY_ATTEMPTS =
      new LoginException("Too many bad login attempts", HttpStatus.TOO_MANY_REQUESTS);

  public static final LoginException IO_ERROR =
      new LoginException("Login failed due to IO error", HttpStatus.INTERNAL_SERVER_ERROR);
}
