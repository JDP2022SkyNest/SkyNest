package com.htecgroup.skynest.exception.object;

import org.springframework.http.HttpStatus;

public class ObjectIsDeletedException extends ObjectException {

  public static final String MESSAGE = "Object is deleted";

  public ObjectIsDeletedException() {
    super(MESSAGE, HttpStatus.NOT_FOUND);
  }
}
