package com.htecgroup.skynest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserExceptionType {
  EMAIL_ALREADY_IN_USE("This email is already in use", HttpStatus.CONFLICT),
  INVALID_PASSWORD_FORMAT("The password doesn't match the required format", HttpStatus.BAD_REQUEST),
  INVALID_EMAIL_OR_PASSWORD("The email and password are not valid", HttpStatus.BAD_REQUEST),

  PASSWORDS_DOES_NOT_MATCH("The provided password doesn't match the username", HttpStatus.EXPECTATION_FAILED);

  private final String message;
  private final HttpStatus status;
}
