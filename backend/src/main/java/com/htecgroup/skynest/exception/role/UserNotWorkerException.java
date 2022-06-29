package com.htecgroup.skynest.exception.role;

import org.springframework.http.HttpStatus;

public class UserNotWorkerException extends RoleException {

  public static final String MESSAGE = "User is not a worker, can't be promoted";

  public UserNotWorkerException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
