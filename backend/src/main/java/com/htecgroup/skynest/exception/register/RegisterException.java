package com.htecgroup.skynest.exception.register;

import com.htecgroup.skynest.exception.CustomException;
import org.springframework.http.HttpStatus;

public class RegisterException extends CustomException {

  protected RegisterException(String message, HttpStatus status) {
    super(message, status);
  }
}
