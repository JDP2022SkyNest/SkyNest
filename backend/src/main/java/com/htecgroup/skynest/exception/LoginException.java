package com.htecgroup.skynest.exception;

import org.springframework.http.HttpStatus;

public class LoginException extends CustomException {

  private LoginException(String message, HttpStatus status) {
    super(message, status);
  }

  public static final LoginException USER_NOT_FOUND =
      new LoginException("User not found", HttpStatus.UNAUTHORIZED);

  public static final LoginException EMAIL_NOT_VERIFIED =
      new LoginException("Email not verified", HttpStatus.FORBIDDEN);

  public static final LoginException WRONG_PASSWORD =
      new LoginException("Wrong password", HttpStatus.BAD_REQUEST);

  public static final LoginException TOO_MANY_ATTEMPTS =
      new LoginException("Too many bad login attempts", HttpStatus.TOO_MANY_REQUESTS);
}
