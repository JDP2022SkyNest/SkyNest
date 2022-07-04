package com.htecgroup.skynest.exception.action;

import org.springframework.http.HttpStatus;

public class ActionNotFoundException extends ActionException {

  private static final long serialVersionUID = -5819964722905162651L;
  private static final String MESSAGE = "Action type doesn't exist";

  public ActionNotFoundException() {
    super(MESSAGE, HttpStatus.NOT_FOUND);
  }
}
