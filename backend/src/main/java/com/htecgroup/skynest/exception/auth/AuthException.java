package com.htecgroup.skynest.exception.auth;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class AuthException extends SkyNestBaseException {

  protected AuthException(String message, HttpStatus status) {
    super(message, status);
  }
}
