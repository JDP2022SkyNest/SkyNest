package com.htecgroup.SkyNest.exception;

import com.htecgroup.SkyNest.model.response.ErrorMessage;
import com.htecgroup.SkyNest.util.DateTimeUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class UserExceptionHandler {

  @ExceptionHandler(value = {UserException.class})
  public ResponseEntity<ErrorMessage> handleUserException(UserException ex, WebRequest webRequest) {
    ErrorMessage errorMessage =
        new ErrorMessage(
            ex.getMessage(), ex.getStatus().value(), DateTimeUtil.currentTimeFormatted());

    return new ResponseEntity<>(errorMessage, ex.getStatus());
  }
}
