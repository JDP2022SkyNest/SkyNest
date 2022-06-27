package com.htecgroup.skynest.exception.role;

import org.springframework.http.HttpStatus;

public class UserNotManagerException extends RoleException {

  private static final String MESSAGE = "User is not a manager, can't be demoted";

  public UserNotManagerException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
