package com.htecgroup.skynest.exception.permission;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class PermissionException extends SkyNestBaseException {

  private static final long serialVersionUID = -4234941138917635972L;

  protected PermissionException(String message, HttpStatus status) {
    super(message, status);
  }
}
