package com.htecgroup.skynest.exception.role;

import com.htecgroup.skynest.exception.CustomException;
import org.springframework.http.HttpStatus;

public class RoleException extends CustomException {
  protected RoleException(String message, HttpStatus status) {
    super(message, status);
  }
}
