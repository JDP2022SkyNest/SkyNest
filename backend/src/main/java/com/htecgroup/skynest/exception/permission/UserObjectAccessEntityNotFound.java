package com.htecgroup.skynest.exception.permission;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class UserObjectAccessEntityNotFound extends SkyNestBaseException {

  public static final String MESSAGE = "User object access entity not found";

  public UserObjectAccessEntityNotFound() {
    super(MESSAGE, HttpStatus.NOT_FOUND);
  }
}
