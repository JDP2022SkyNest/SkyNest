package com.htecgroup.skynest.exception;

import org.springframework.http.HttpStatus;

public class UserException extends CustomException {
  private UserException(String message, HttpStatus status) {
    super(message, status);
  }

  public static final UserException USER_NOT_FOUND =
      new UserException("User not found", HttpStatus.NOT_FOUND);
}
