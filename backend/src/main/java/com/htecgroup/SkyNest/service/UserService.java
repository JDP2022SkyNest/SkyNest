package com.htecgroup.SkyNest.service;

import com.htecgroup.SkyNest.model.dto.UserDto;

public interface UserService {

  String registerUser(UserDto userDto);

  UserDto findUserByEmail(String email);

  String confirmEmail(String token);
}
