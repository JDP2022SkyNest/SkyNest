package com.htecgroup.skynest.filter;

import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.model.response.ErrorMessage;
import com.htecgroup.skynest.service.InvalidJwtService;
import com.htecgroup.skynest.util.DateTimeUtil;
import com.htecgroup.skynest.util.ExceptionUtil;
import com.htecgroup.skynest.util.JwtUtils;
import com.htecgroup.skynest.util.UrlUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class CustomAuthorizationFilter extends OncePerRequestFilter {

  // I'm using @Autowired because using constructor DI doesn't work.
  // I think it has something to do with the way CustomAuthorizationFilter is defined as a bean in
  // the application class, as it calls the NoArgsConstructor and the dependencies are not loaded.
  // This way the service is autowired and it works.
  @Autowired private InvalidJwtService invalidJwtService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    try {
      if (request
          .getServletPath()
          .startsWith(String.format("%s/", UrlUtil.PUBLIC_CONTROLLER_URL))) {
        filterChain.doFilter(request, response);
        return;
      }

      String authorizationHeader = request.getHeader(JwtUtils.AUTH_HEADER);
      if (authorizationHeader == null) {
        filterChain.doFilter(request, response);
        return;
      }

      if (!authorizationHeader.startsWith(JwtUtils.TOKEN_PREFIX)) {
        filterChain.doFilter(request, response);
        return;
      }
      String token = authorizationHeader.replace(JwtUtils.TOKEN_PREFIX, "");

      if (invalidJwtService.isInvalid(token)) {
        filterChain.doFilter(request, response);
        log.info(
            "Token {} is invalid, because it's present in invalidated tokens database.", token);
        return;
      }

      SecurityContextHolder.getContext().setAuthentication(JwtUtils.getFrom(token));

      filterChain.doFilter(request, response);
    } catch (UserException ex) {
      log.error(ex);
      ErrorMessage errorMessage =
          new ErrorMessage(
              ex.getMessage(), ex.getStatus().value(), DateTimeUtil.currentTimeFormatted());
      ExceptionUtil.writeToResponse(errorMessage, response);
    } catch (Exception ex) {
      log.error(ex);
      ErrorMessage errorMessage =
          new ErrorMessage(
              ex.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR.value(),
              DateTimeUtil.currentTimeFormatted());
      ExceptionUtil.writeToResponse(errorMessage, response);
    }
  }
}
