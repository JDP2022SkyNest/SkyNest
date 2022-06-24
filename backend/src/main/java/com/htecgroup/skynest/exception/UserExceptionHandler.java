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

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class UserExceptionHandler {

  @ExceptionHandler(value = {UserException.class})
  public ResponseEntity<ErrorMessage> handleUserException(UserException ex, WebRequest webRequest) {
    log.error("Handle UserException. Error: {}", ex.getMessage());
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

  @ExceptionHandler(value = {ConstraintViolationException.class})
  public ResponseEntity<ErrorMessage> handleConstraintViolationException(
      ConstraintViolationException ex) {

    List<String> errors =
        ex.getConstraintViolations().stream()
            .map(error -> error.getMessage())
            .collect(Collectors.toList());
    ErrorMessage errorMessage =
        new ErrorMessage(errors, HttpStatus.FORBIDDEN.value(), DateTimeUtil.currentTimeFormatted());
    log.error("Handle MethodArgumentNotValidException. Error: {}", ex.getMessage());
    return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
  }
}
