package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.response.LoggedUserResponse;
import com.htecgroup.skynest.service.CurrentUserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CurrentUserServiceImpl implements CurrentUserService {

  private ModelMapper modelMapper;

  public LoggedUserDto getLoggedUser() {
    return (LoggedUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  public LoggedUserResponse getUserResponseForLoggedUser() {
    LoggedUserResponse loggedUserResponse =
        modelMapper.map(getLoggedUser(), LoggedUserResponse.class);
    return loggedUserResponse.withRoles(getLoggedUser().getRoleNames());
  }
}
