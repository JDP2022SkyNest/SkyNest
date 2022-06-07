package com.htecgroup.skynest.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.exception.UserExceptionHandler;
import com.htecgroup.skynest.model.response.ErrorMessage;
import com.htecgroup.skynest.util.JwtUtils;
import com.htecgroup.skynest.util.UrlUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Log4j2
public class CustomAuthorizationFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    try {
      if (Arrays.stream(UrlUtil.URLS_WITHOUT_AUTH)
          .anyMatch(url -> url.equals(request.getServletPath()))) {
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
      ResponseEntity<ErrorMessage> responseEntity = UserExceptionHandler.responseFor(ex);
      UserExceptionHandler.writeToResponse(responseEntity, response);
    } catch (Exception e) {
      log.error(e);
      new ObjectMapper().writeValue(response.getOutputStream(), "Internal Error.");
    }
  }
}
