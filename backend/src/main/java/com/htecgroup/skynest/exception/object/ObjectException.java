package com.htecgroup.skynest.exception.object;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class ObjectException extends SkyNestBaseException {

  protected ObjectException(String message, HttpStatus status) {
    super(message, status);
  }
}
