package com.htecgroup.skynest.exception.action;

import org.springframework.http.HttpStatus;

public class ActionTypeNotFound extends ActionException {

  private static final long serialVersionUID = 5847907416379815446L;

  private static final String MESSAGE = "Action type doesn't exist";

  public ActionTypeNotFound() {
    super(MESSAGE, HttpStatus.NOT_FOUND);
  }
}
