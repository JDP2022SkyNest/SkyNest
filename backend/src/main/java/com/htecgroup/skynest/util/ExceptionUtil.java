package com.htecgroup.skynest.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.htecgroup.skynest.model.response.ErrorMessage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionUtil {
  public static void writeToResponse(ErrorMessage errorMessage, HttpServletResponse response)
      throws IOException {
    response.setStatus(errorMessage.getStatus());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    String jsonBody = new ObjectMapper().writeValueAsString(errorMessage);
    response.getWriter().write(jsonBody);
    response.flushBuffer();
  }
}
