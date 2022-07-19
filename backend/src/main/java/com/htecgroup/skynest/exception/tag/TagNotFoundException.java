package com.htecgroup.skynest.exception.tag;

import org.springframework.http.HttpStatus;

public class TagNotFoundException extends TagException {

  public static final String MESSAGE = "Tag doesn't exist";

  public TagNotFoundException() {
    super(MESSAGE, HttpStatus.NOT_FOUND);
  }
}
