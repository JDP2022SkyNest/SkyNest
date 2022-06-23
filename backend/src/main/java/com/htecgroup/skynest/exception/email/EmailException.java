package com.htecgroup.skynest.exception.email;

import com.htecgroup.skynest.exception.CustomException;
import org.springframework.http.HttpStatus;

public class EmailException extends CustomException {

  protected EmailException(String message, HttpStatus status) {
    super(message, status);
  }
}
