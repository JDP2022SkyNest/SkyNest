package com.htecgroup.skynest.exception;

import org.springframework.http.HttpStatus;

public class RoleException extends CustomException {
  private RoleException(String message, HttpStatus status) {
    super(message, status);
  }

  public static final RoleException ROLE_NOT_FOUND =
      new RoleException("User role doesn't exist", HttpStatus.NOT_FOUND);
}
