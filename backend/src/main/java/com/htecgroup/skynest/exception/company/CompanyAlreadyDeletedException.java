package com.htecgroup.skynest.exception.company;

import org.springframework.http.HttpStatus;

public class CompanyAlreadyDeletedException extends CompanyException {

  private static final String MESSAGE = "Company was already deleted";

  public CompanyAlreadyDeletedException() {
    super(MESSAGE, HttpStatus.CONFLICT);
  }
}
