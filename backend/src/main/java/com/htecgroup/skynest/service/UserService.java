package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.dto.UserDto;

public interface UserService {

  String registerUser(UserDto userDto);

  UserDto findUserByEmail(String email);

  String confirmEmail(String token);
}
