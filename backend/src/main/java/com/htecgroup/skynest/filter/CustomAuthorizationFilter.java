package com.htecgroup.skynest.filter;

import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.model.response.ErrorMessage;
import com.htecgroup.skynest.util.DateTimeUtil;
import com.htecgroup.skynest.util.ExceptionUtil;
import com.htecgroup.skynest.util.JwtUtils;
import com.htecgroup.skynest.util.UrlUtil;
import lombok.extern.log4j.Log4j2;
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
