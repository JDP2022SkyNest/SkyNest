package com.htecgroup.skynest.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.htecgroup.skynest.model.response.ErrorMessage;
import com.htecgroup.skynest.util.DateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class UserException extends RuntimeException {

  private static final long serialVersionUID = 2371435714335613721L;
  private final String message;
  private final HttpStatus status;

  public UserException(UserExceptionType userExceptionType) {
    message = userExceptionType.getMessage();
    status = userExceptionType.getStatus();
  }

  public ResponseEntity<ErrorMessage> asResponseEntity() {
    ErrorMessage errorMessage =
        new ErrorMessage(message, status.value(), DateTimeUtil.currentTimeFormatted());
    return new ResponseEntity<>(errorMessage, status);
  }

  public void writeToResponse(HttpServletResponse response) throws IOException {
    ResponseEntity<ErrorMessage> responseEntity = this.asResponseEntity();
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
}
