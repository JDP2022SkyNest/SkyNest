package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.exception.UserExceptionType;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.service.RefreshTokenService;
import com.htecgroup.skynest.util.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.htecgroup.skynest.util.JwtUtils.ACCESS_TOKEN_EXPIRATION_MS;

@Service
@AllArgsConstructor
@Log4j2
public class RefreshTokenServiceImpl implements RefreshTokenService {

  private CurrentUserService currentUserService;
  private ModelMapper modelMapper;

  @Override
  public String refreshToken(String refresh_token) {
    if (refresh_token != null && refresh_token.startsWith("Bearer ")) {
      try {
        LoggedUserDto loggedUserDto = currentUserService.getLoggedUser();

        List<String> authorities =
            loggedUserDto.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        String access_token =
            JwtUtils.generate(
                loggedUserDto.getUsername(), ACCESS_TOKEN_EXPIRATION_MS, "roles", authorities);
        return access_token;
      } catch (Exception exception) {
        log.error(exception.getMessage(), exception);
      }
    } else {
      throw new UserException(UserExceptionType.REFRESH_TOKEN_IS_MISSING);
    }
    return "";
  }
}
