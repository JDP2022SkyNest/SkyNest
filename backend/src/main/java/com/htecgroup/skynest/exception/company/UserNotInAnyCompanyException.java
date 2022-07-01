package com.htecgroup.skynest.exception.company;

import org.springframework.http.HttpStatus;

public class UserNotInAnyCompanyException extends CompanyException {

  private static final String MESSAGE = "User is not a part of any company";

  public UserNotInAnyCompanyException() {
    super(MESSAGE, HttpStatus.NOT_FOUND);
  }
}
