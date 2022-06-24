package com.htecgroup.skynest.exception.login;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class LoginException extends SkyNestBaseException {

  protected LoginException(String message, HttpStatus status) {
    super(message, status);
  }
}
