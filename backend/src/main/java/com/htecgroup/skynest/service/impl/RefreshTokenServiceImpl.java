package com.htecgroup.skynest.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.service.RefreshTokenService;
import com.htecgroup.skynest.service.UserService;
import com.htecgroup.skynest.util.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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

        // TODO: somehow get the authorities to pass them inside the token
        UserDto userDto = userService.findUserByEmail(username);
        List<String> authorities = new ArrayList<>();
        authorities.add(userDto.getRole().toString());

        String access_token =
            JwtUtils.generate(username, ACCESS_TOKEN_EXPIRATION_MS, "roles", authorities);
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
