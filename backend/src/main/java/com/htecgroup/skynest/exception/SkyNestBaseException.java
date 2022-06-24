package com.htecgroup.skynest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class SkyNestBaseException extends RuntimeException {

  private final String message;
  private final HttpStatus status;
}
