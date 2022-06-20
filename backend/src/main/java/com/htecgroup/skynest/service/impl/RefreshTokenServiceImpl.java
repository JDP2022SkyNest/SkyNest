package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.exception.UserExceptionType;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.jwtObject.JwtObject;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.service.RefreshTokenService;
import com.htecgroup.skynest.util.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class RefreshTokenServiceImpl implements RefreshTokenService {

  private CurrentUserService currentUserService;
  private ModelMapper modelMapper;

  @Override
  public String refreshToken(String refresh_token) {
    if (refresh_token != null && refresh_token.startsWith(JwtUtils.TOKEN_PREFIX)) {
      try {
        LoggedUserDto loggedUserDto = (LoggedUserDto) currentUserService.getLoggedUser();
        List<String> authorities = loggedUserDto.getRoleNames();

        JwtObject jwtObject = new JwtObject(loggedUserDto.getUuid(), loggedUserDto.getUsername());

        String accessToken = JwtUtils.generateAccessToken(jwtObject, authorities);

        return accessToken;
      } catch (Exception exception) {
        log.error(exception.getMessage(), exception);
      }
    } else {
      throw new UserException(UserExceptionType.REFRESH_TOKEN_IS_MISSING);
    }
    return "";
  }
}
