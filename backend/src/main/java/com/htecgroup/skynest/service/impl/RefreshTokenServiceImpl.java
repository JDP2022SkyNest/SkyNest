package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.exception.UserExceptionType;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.jwtObject.JwtObject;
import com.htecgroup.skynest.service.RefreshTokenService;
import com.htecgroup.skynest.service.UserService;
import com.htecgroup.skynest.util.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class RefreshTokenServiceImpl implements RefreshTokenService {

  private UserService userService;
  private InvalidJwtServiceImpl invalidJwtService;

  @Override
  public String refreshToken(String refreshToken) {
    if (refreshToken != null && refreshToken.startsWith(JwtUtils.TOKEN_PREFIX)) {
      try {
        String refresh_token = refreshToken.replace(JwtUtils.TOKEN_PREFIX, "");
        String username = JwtUtils.getUsernameFromRefreshToken(refresh_token);
        List<String> authorities =
            JwtUtils.getAuthoritiesFromRefreshToken(refresh_token).stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        UserDto userDto = userService.findUserByEmail(username);
        UUID id = userDto.getId();

        JwtObject jwtObject = new JwtObject(id, username);
        String newAccessToken = JwtUtils.generateAccessToken(jwtObject, authorities);
        return newAccessToken;
      } catch (Exception exception) {
        log.error(exception.getMessage(), exception);
      }
    } else {
      throw new UserException(UserExceptionType.REFRESH_TOKEN_IS_MISSING);
    }
    return null;
  }
}
