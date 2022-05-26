package com.htecgroup.SkyNest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserExceptionType {
  EMAIL_ALREADY_IN_USE("This email is already in use", HttpStatus.CONFLICT),
  INVALID_PASSWORD_FORMAT("The password doesn't match the required format", HttpStatus.BAD_REQUEST);

  private final String message;
  private final HttpStatus status;
}
