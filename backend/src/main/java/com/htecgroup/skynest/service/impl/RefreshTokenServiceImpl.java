package com.htecgroup.skynest.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.htecgroup.skynest.service.RefreshTokenService;
import com.htecgroup.skynest.service.UserService;
import com.htecgroup.skynest.util.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

import static com.htecgroup.skynest.util.JwtUtils.ACCESS_TOKEN_EXPIRATION_MS;
import static com.htecgroup.skynest.util.JwtUtils.ALGORITHM;

@Service
@AllArgsConstructor
@Log4j2
public class RefreshTokenServiceImpl implements RefreshTokenService {

  private UserService userService;
  private ModelMapper modelMapper;

  @Override
  public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
    String header = request.getHeader("refresh-token");
    if (header != null && header.startsWith("Bearer ")) {
      try {
        String refresh_token = header.substring("Bearer ".length());
        DecodedJWT decodedJWT = JWT.require(ALGORITHM).build().verify(refresh_token);
        String username = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        User user = modelMapper.map(userService.findUserByEmail(username), User.class);
        List<String> authorities =
            user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String access_token =
            JwtUtils.generate(user.getUsername(), ACCESS_TOKEN_EXPIRATION_MS, "roles", authorities);
        response.addHeader(
            JwtUtils.AUTH_HEADER, String.format("%s%s", JwtUtils.TOKEN_PREFIX, access_token));
        response.addHeader(
            "refresh-token", String.format("%s%s", JwtUtils.TOKEN_PREFIX, refresh_token));
      } catch (Exception exception) {
        log.error(exception.getMessage(), exception);
      }
    } else {
      throw new RuntimeException("Refresh token is missing");
    }
  }
}
