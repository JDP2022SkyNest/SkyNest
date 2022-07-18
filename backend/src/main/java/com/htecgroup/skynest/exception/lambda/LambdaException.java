package com.htecgroup.skynest.exception.lambda;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class LambdaException extends SkyNestBaseException {

  public LambdaException(String message, HttpStatus status) {
    super(message, status);
  }
}
