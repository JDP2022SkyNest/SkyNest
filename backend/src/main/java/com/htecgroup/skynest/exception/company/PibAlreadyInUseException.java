package com.htecgroup.skynest.exception.company;

import org.springframework.http.HttpStatus;

public class PibAlreadyInUseException extends CompanyException {

  private static final String MESSAGE = "Pib already in use";

  public PibAlreadyInUseException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
