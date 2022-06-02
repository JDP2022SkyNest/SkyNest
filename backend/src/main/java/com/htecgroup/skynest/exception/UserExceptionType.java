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

  PASSWORDS_DOES_NOT_MATCH(
      "The provided password doesn't match the username", HttpStatus.EXPECTATION_FAILED),
  EMAIL_FAILED_TO_SEND(
      "The email failed to send from the Email server", HttpStatus.INTERNAL_SERVER_ERROR),
  USER_ALREADY_REGISTERED(
      "User is already registered, verified and enabled", HttpStatus.INTERNAL_SERVER_ERROR),
  EMAIL_VERIFICATION_TOKEN_FAILED(
      "Jwt token failed the validation. For more information check the logger",
      HttpStatus.INTERNAL_SERVER_ERROR);

  private final String message;
  private final HttpStatus status;
}
