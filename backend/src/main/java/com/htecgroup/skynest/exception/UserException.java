package com.htecgroup.skynest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class UserException extends RuntimeException {

  private String message;
  private HttpStatus status;

  public UserException(UserExceptionType userExceptionType) {
    message = userExceptionType.getMessage();
    status = userExceptionType.getStatus();
  }
}
