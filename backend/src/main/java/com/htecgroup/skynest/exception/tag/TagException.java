package com.htecgroup.skynest.exception.tag;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class TagException extends SkyNestBaseException {

  private static final long serialVersionUID = -4284721341854147103L;

  protected TagException(String message, HttpStatus status) {
    super(message, status);
  }
}
