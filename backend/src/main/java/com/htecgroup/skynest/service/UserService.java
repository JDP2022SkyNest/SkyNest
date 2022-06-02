package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.dto.UserDto;
public interface UserService {

  UserDto registerUser(UserDto userDto);

  UserDto findUserByEmail(String email);

  String confirmEmail(String token);

  UserDto enableUser(UserDto userDto);
  void sendVerificationEmail(String email);
}
