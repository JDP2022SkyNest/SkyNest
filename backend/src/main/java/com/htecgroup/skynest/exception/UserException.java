package com.htecgroup.skynest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class UserException extends RuntimeException {

  private static final long serialVersionUID = 2371435714335613721L;
  private final String message;
  private final HttpStatus status;

  public UserException(UserExceptionType userExceptionType) {
    message = userExceptionType.getMessage();
    status = userExceptionType.getStatus();
  }
}
