package com.htecgroup.skynest.exception.company;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class CompanyException extends SkyNestBaseException {

  protected CompanyException(String message, HttpStatus status) {
    super(message, status);
  }
}
