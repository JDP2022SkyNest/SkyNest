package com.htecgroup.skynest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserExceptionType {
  EMAIL_ALREADY_IN_USE("This email is already in use", HttpStatus.CONFLICT),
  INVALID_PASSWORD_FORMAT("The password doesn't match the required format", HttpStatus.BAD_REQUEST),
  EMAIL_NOT_VALID("The email doesn't match the required format", HttpStatus.BAD_REQUEST),
  NAME_NOT_VALID("The name doesn't match the required format", HttpStatus.BAD_REQUEST),
  SURNAME_NOT_VALID("The surname doesn't match the required format", HttpStatus.BAD_REQUEST),
  ADRESS_NOT_VALID("The adress doesn't match the required format", HttpStatus.BAD_REQUEST),
  PHONE_NOT_VALID("The phone number doesn't match the required format", HttpStatus.BAD_REQUEST);

  private final String message;
  private final HttpStatus status;
}
