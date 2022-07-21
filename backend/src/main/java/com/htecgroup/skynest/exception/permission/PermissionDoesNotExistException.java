package com.htecgroup.skynest.exception.permission;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class PermissionDoesNotExistException extends SkyNestBaseException {

  public static final String MESSAGE = "Permission doesn't exist.";

  public PermissionDoesNotExistException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
