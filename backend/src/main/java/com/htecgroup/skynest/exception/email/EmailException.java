package com.htecgroup.skynest.exception.email;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class EmailException extends SkyNestBaseException {

  protected EmailException(String message, HttpStatus status) {
    super(message, status);
  }
}
