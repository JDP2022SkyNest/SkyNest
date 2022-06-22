package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.exception.UserExceptionType;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.jwtObject.JwtObject;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.service.InvalidJwtService;
import com.htecgroup.skynest.service.RefreshTokenService;
import com.htecgroup.skynest.util.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class RefreshTokenServiceImpl implements RefreshTokenService {

  private CurrentUserService currentUserService;
  private InvalidJwtService invalidJwtService;

  @Override
  public String refreshToken(String refreshToken, String invalidToken) {
    if (refreshToken != null && refreshToken.startsWith(JwtUtils.TOKEN_PREFIX)) {
      try {
        String tokenForInvalidation = invalidToken.replace(JwtUtils.TOKEN_PREFIX, "");
        invalidJwtService.invalidate(tokenForInvalidation);
        LoggedUserDto loggedUserDto = currentUserService.getLoggedUser();
        List<String> authorities = loggedUserDto.getRoleNames();

        JwtObject jwtObject = new JwtObject(loggedUserDto.getUuid(), loggedUserDto.getUsername());

        String newAccessToken = JwtUtils.generateAccessToken(jwtObject, authorities);

        return newAccessToken;
      } catch (Exception exception) {
        log.error(exception.getMessage(), exception);
      }
    } else {
      throw new UserException(UserExceptionType.REFRESH_TOKEN_IS_MISSING);
    }
    return "";
  }
}
