package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.response.LoggedUserResponse;

public interface CurrentUserService {

  LoggedUserDto getLoggedUser();

  LoggedUserResponse getUserResponseForLoggedUser();
}
