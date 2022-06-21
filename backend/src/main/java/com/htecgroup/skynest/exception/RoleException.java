package com.htecgroup.skynest.exception;

import org.springframework.http.HttpStatus;

public class RoleException extends CustomException {
  private RoleException(String message, HttpStatus status) {
    super(message, status);
  }
}
