package com.htecgroup.skynest.exception.register;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class RegisterException extends SkyNestBaseException {

  protected RegisterException(String message, HttpStatus status) {
    super(message, status);
  }
}
