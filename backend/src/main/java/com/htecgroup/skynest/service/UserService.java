package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.dto.UserDto;

import java.util.List;

public interface UserService {

  UserDto registerUser(UserDto userDto);

  UserDto findUserByEmail(String email);

  List<UserDto> listAllUsers();

  String confirmEmail(String token);

  UserDto enableUser(UserDto userDto);
  void sendVerificationEmail(String email);
}
