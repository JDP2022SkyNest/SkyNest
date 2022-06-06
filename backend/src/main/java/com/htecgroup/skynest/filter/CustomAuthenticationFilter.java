package com.htecgroup.skynest.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.exception.UserExceptionType;
import com.htecgroup.skynest.model.request.UserLoginRequest;
import com.htecgroup.skynest.service.UserService;
import com.htecgroup.skynest.util.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Log4j2
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  private final UserService userService;

  private final ObjectMapper objectMapper;

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    UserLoginRequest credentials = new UserLoginRequest();

    try {
      credentials = objectMapper.readValue(request.getInputStream(), UserLoginRequest.class);

    } catch (IOException e) {
      log.error("Unable to authenticate, because of Input or Output error", e);
    }
    UserDetails userDetails = userDetailsService.loadUserByUsername(credentials.getEmail());
    log.info("The user: {} is trying to authenticate.", credentials.getEmail());
    if (!credentials.getEmail().equals(userDetails.getUsername())) {
      throw new UserException(UserExceptionType.INVALID_EMAIL_OR_PASSWORD);
    }

    if (!passwordEncoder.matches(credentials.getPassword(), userDetails.getPassword())) {
      throw new UserException(UserExceptionType.PASSWORDS_DOES_NOT_MATCH);
    }

    if (!userService.isActive(credentials.getEmail()))
      throw new UserException(UserExceptionType.USER_NOT_ACTIVE);

    return authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            credentials.getEmail(), credentials.getPassword(), userDetails.getAuthorities()));
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authentication)
      throws IOException, ServletException {

    User user = (User) authentication.getPrincipal();

    List<String> authorities =
        user.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
    String token =
        JwtUtils.generate(
            user.getUsername(), JwtUtils.ACCESS_TOKEN_EXPIRATION_MS, "roles", authorities);

    response.addHeader(JwtUtils.HEADER_STRING, JwtUtils.TOKEN_PREFIX + token);
    log.info("Jwt token successfully created for user: {}", user.getUsername());

    log.info("{} is successfully logged in.", user.getUsername());
  }
}
