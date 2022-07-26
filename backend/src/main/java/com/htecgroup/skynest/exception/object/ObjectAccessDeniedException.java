package com.htecgroup.skynest.exception.object;

import org.springframework.http.HttpStatus;

public class ObjectAccessDeniedException extends ObjectException {

  private static final long serialVersionUID = -4352290087494862319L;

  private static final String MESSAGE = "User does not have access to object";

  public ObjectAccessDeniedException() {
    super(MESSAGE, HttpStatus.FORBIDDEN);
  }
}
