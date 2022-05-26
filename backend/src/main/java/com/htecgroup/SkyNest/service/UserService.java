package com.htecgroup.SkyNest.service;

import com.htecgroup.SkyNest.model.dto.UserDto;

public interface UserService {

  UserDto registerUser(UserDto userDto);

  UserDto findUserByEmail(String email);

}
