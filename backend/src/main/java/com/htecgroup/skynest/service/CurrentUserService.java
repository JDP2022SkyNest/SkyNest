package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.entity.CompanyEntity;
import com.htecgroup.skynest.model.response.LoggedUserResponse;

import java.util.Optional;

public interface CurrentUserService {

  LoggedUserDto getLoggedUser();

  LoggedUserResponse getUserResponseForLoggedUser();

  Optional<CompanyEntity> getCompanyEntityFromLoggedUser();
}
