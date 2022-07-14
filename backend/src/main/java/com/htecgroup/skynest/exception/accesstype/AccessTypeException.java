package com.htecgroup.skynest.exception.accesstype;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class AccessTypeException extends SkyNestBaseException {

  private static final long serialVersionUID = -6920883293393177324L;

  protected AccessTypeException(String message, HttpStatus status) {
    super(message, status);
  }
}
