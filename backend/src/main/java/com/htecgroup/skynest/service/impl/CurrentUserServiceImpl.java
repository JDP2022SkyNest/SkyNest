package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.entity.CompanyEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.model.response.LoggedUserResponse;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.CurrentUserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CurrentUserServiceImpl implements CurrentUserService {

  private ModelMapper modelMapper;
  private UserRepository userRepository;

  public LoggedUserDto getLoggedUser() {
    return (LoggedUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  public LoggedUserResponse getUserResponseForLoggedUser() {
    LoggedUserResponse loggedUserResponse =
        modelMapper.map(getLoggedUser(), LoggedUserResponse.class);
    return loggedUserResponse.withRoles(getLoggedUser().getRoleNames());
  }

  @Override
  public Optional<CompanyEntity> getCompanyEntityFromLoggedUser() {
    LoggedUserDto loggedUserDto = getLoggedUser();
    UserEntity userEntity = userRepository.getById(loggedUserDto.getUuid());
    return Optional.ofNullable(userEntity.getCompany());
  }
}
