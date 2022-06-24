package com.htecgroup.skynest.exception.role;

import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends RoleException {

  private static final String MESSAGE = "User role doesn't exist";

  public RoleNotFoundException() {
    super(MESSAGE, HttpStatus.NOT_FOUND);
  }
}
