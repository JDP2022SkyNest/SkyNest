package com.htecgroup.skynest.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.htecgroup.skynest.model.response.ErrorMessage;
import com.htecgroup.skynest.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class UserExceptionHandler {

  public static ResponseEntity<ErrorMessage> responseFor(UserException ex) {
    return new UserExceptionHandler().handleUserException(ex, null);
  }

  public static void writeToResponse(
      ResponseEntity<ErrorMessage> responseEntity, HttpServletResponse response)
      throws IOException {
    for (Map.Entry<String, List<String>> header : responseEntity.getHeaders().entrySet()) {
      String headerName = header.getKey();
      for (String headerValue : header.getValue()) {
        response.addHeader(headerName, headerValue);
      }
    }
    response.setStatus(responseEntity.getStatusCodeValue());

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    String jsonBody = new ObjectMapper().writeValueAsString(responseEntity.getBody());
    response.getWriter().write(jsonBody);
    response.flushBuffer();
  }

  @ExceptionHandler(value = {UserException.class})
  public ResponseEntity<ErrorMessage> handleUserException(UserException ex, WebRequest webRequest) {
    ErrorMessage errorMessage =
        new ErrorMessage(
            ex.getMessage(), ex.getStatus().value(), DateTimeUtil.currentTimeFormatted());
    log.error("Handle UserException. Error: {}", ex.getMessage());
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
