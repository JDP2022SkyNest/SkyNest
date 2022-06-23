package com.htecgroup.skynest.exception.auth;

import com.htecgroup.skynest.exception.CustomException;
import org.springframework.http.HttpStatus;

public class AuthException extends CustomException {

  protected AuthException(String message, HttpStatus status) {
    super(message, status);
  }
}
