package com.htecgroup.skynest.exception.tier;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class NonExistingTierException extends SkyNestBaseException {

  public static final String MESSAGE = "Tier doesn't exist";

  public NonExistingTierException() {
    super(MESSAGE, HttpStatus.NOT_FOUND);
  }
}
