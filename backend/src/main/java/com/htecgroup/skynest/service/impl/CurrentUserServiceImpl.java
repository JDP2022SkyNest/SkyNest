package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.service.CurrentUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CurrentUserServiceImpl implements CurrentUserService {

  public LoggedUserDto getLoggedUser() {
    return (LoggedUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
