package com.htecgroup.skynest.exception.company;

import org.springframework.http.HttpStatus;

public class CompanyNotFoundException extends CompanyException {

  private static final String MESSAGE = "Company not found";

  public CompanyNotFoundException() {
    super(MESSAGE, HttpStatus.NOT_FOUND);
  }
}