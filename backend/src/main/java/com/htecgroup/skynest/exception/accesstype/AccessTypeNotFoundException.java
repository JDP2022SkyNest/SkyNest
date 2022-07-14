package com.htecgroup.skynest.exception.accesstype;

import com.htecgroup.skynest.exception.action.ActionException;
import org.springframework.http.HttpStatus;

public class AccessTypeNotFoundException extends ActionException {

  private static final long serialVersionUID = 5847907416379815446L;

  private static final String MESSAGE = "Access type doesn't exist";

  public AccessTypeNotFoundException() {
    super(MESSAGE, HttpStatus.NOT_FOUND);
  }
}
