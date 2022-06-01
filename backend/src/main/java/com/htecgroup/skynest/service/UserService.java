package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.dto.UserDto;

import java.util.ArrayList;

public interface UserService {

  UserDto registerUser(UserDto userDto);

  UserDto findUserByEmail(String email);

  ArrayList<UserDto> listAllUsers();
}
