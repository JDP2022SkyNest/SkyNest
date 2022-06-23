package com.htecgroup.skynest.exception.login;

import com.htecgroup.skynest.exception.CustomException;
import org.springframework.http.HttpStatus;

public class LoginException extends CustomException {

  protected LoginException(String message, HttpStatus status) {
    super(message, status);
  }
}
