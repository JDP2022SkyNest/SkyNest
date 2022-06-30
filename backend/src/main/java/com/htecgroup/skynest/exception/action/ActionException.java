package com.htecgroup.skynest.exception.action;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class ActionException extends SkyNestBaseException {

  private static final long serialVersionUID = -358348490163929040L;

  protected ActionException(String message, HttpStatus status) {
    super(message, status);
  }
}
