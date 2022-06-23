package com.htecgroup.skynest.exception;

import com.htecgroup.skynest.model.response.ErrorMessage;
import com.htecgroup.skynest.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

  @ExceptionHandler(value = {CustomException.class})
  public ResponseEntity<ErrorMessage> handleUserException(
      CustomException ex, WebRequest webRequest) {
    log.error("Handle CustomException. Error: {}", ex.getMessage());
    ErrorMessage errorMessage =
        new ErrorMessage(
            ex.getMessage(), ex.getStatus().value(), DateTimeUtil.currentTimeFormatted());
    return new ResponseEntity<>(errorMessage, ex.getStatus());
  }

  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  public ResponseEntity<ErrorMessage> handleValidationException(
      MethodArgumentNotValidException ex) {
    List<String> errors =
        ex.getBindingResult().getAllErrors().stream()
            .map(
                error ->
                    String.format(
                        "%s %s", ((FieldError) error).getField(), error.getDefaultMessage()))
            .collect(Collectors.toList());
    ErrorMessage errorMessage =
        new ErrorMessage(
            errors, HttpStatus.BAD_REQUEST.value(), DateTimeUtil.currentTimeFormatted());
    log.error("Handle MethodArgumentNotValidException. Error: {}", ex.getMessage());
    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
  }
}
