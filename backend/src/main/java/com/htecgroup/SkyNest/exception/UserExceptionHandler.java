package com.htecgroup.SkyNest.exception;

import com.htecgroup.SkyNest.model.response.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class UserExceptionHandler {

  private static final DateTimeFormatter formatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @ExceptionHandler(value = {UserException.class})
  public ResponseEntity<ErrorMessage> handleUserException(UserException ex, WebRequest webRequest) {
    ErrorMessage errorMessage =
        new ErrorMessage(
            ex.getUserExceptionType().getMessage(),
            ex.getUserExceptionType().getStatus().value(),
            LocalDateTime.now().format(formatter));
    return new ResponseEntity<>(errorMessage, ex.getUserExceptionType().getStatus());
  }
}
