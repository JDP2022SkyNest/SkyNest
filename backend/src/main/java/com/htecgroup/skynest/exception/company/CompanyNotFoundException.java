package com.htecgroup.skynest.exception.company;

import org.springframework.http.HttpStatus;

public class CompanyNotFoundException extends CompanyException {

  public static final String MESSAGE = "Company doesn't exist";

  public CompanyNotFoundException() {
    super(MESSAGE, HttpStatus.NOT_FOUND);
  }
}
