package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.exception.UserExceptionType;
import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.CurrentUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CurrentUserServiceImpl implements CurrentUserService {

  private UserRepository userRepository;

  @Override
  public Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  @Override
  public String getEmail() {
    return getAuthentication().getPrincipal().toString();
  }

  @Override
  public String getRole() {
    return new ArrayList<>(getAuthentication().getAuthorities()).get(0).getAuthority();
  }

  @Override
  public UUID getId() {
    UserEntity userEntity =
        userRepository
            .findUserByEmail(getEmail())
            .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));
    return userEntity.getId();
  }
}
