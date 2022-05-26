package com.htecgroup.SkyNest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserException extends RuntimeException {

  private UserExceptionType userExceptionType;
}
