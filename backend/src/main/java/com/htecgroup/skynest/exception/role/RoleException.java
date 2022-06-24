package com.htecgroup.skynest.exception.role;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class RoleException extends SkyNestBaseException {
  protected RoleException(String message, HttpStatus status) {
    super(message, status);
  }
}
