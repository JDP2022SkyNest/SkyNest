package com.htecgroup.skynest.exception.permission;

import com.htecgroup.skynest.exception.action.ActionException;
import org.springframework.http.HttpStatus;

public class PermissionAlreadyExistsException extends ActionException {

  private static final long serialVersionUID = -2005556949503604098L;
  private static final String MESSAGE = "User already has some kind of access to requested object";

  public PermissionAlreadyExistsException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
