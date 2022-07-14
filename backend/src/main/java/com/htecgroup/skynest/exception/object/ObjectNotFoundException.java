package com.htecgroup.skynest.exception.object;

import org.springframework.http.HttpStatus;

public class ObjectNotFoundException extends ObjectException {

  public static final String MESSAGE = "Object not found";

  public ObjectNotFoundException() {
    super(MESSAGE, HttpStatus.NOT_FOUND);
  }
}
