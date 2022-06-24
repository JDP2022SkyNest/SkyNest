package com.htecgroup.skynest.exception.jwt;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import org.springframework.http.HttpStatus;

public class JwtException extends SkyNestBaseException {

  protected JwtException(String message, HttpStatus status) {
    super(message, status);
  }
}
