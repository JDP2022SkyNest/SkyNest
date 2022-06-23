package com.htecgroup.skynest.exception.jwt;

import com.htecgroup.skynest.exception.CustomException;
import org.springframework.http.HttpStatus;

public class JwtException extends CustomException {

  protected JwtException(String message, HttpStatus status) {
    super(message, status);
  }
}
