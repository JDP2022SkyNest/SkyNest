package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.jwt.MissingRefreshToken;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.jwtObject.JwtObject;
import com.htecgroup.skynest.service.RefreshTokenService;
import com.htecgroup.skynest.service.UserService;
import com.htecgroup.skynest.util.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Log4j2
public class RefreshTokenServiceImpl implements RefreshTokenService {

  private UserService userService;
  private InvalidJwtServiceImpl invalidJwtService;
  private ModelMapper modelMapper;

  @Override
  public String refreshToken(String refreshToken) {
    if (refreshToken != null && refreshToken.startsWith(JwtUtils.TOKEN_PREFIX)) {
      try {
        String refresh_token = refreshToken.replace(JwtUtils.TOKEN_PREFIX, "");
        String username = JwtUtils.getUsernameFromRefreshToken(refresh_token);
        UserDto userDto = userService.findUserByEmail(username);
        List<String> authorities = new ArrayList<>();
        authorities.add(userDto.getRole().getName());

        UUID id = userDto.getId();

        JwtObject jwtObject = new JwtObject(id, username);
        String newAccessToken = JwtUtils.generateAccessToken(jwtObject, authorities);
        return newAccessToken;
      } catch (Exception exception) {
        log.error(exception.getMessage(), exception);
      }
    } else {
      throw new MissingRefreshToken();
    }
    return null;
  }
}
