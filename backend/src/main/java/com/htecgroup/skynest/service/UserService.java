package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.request.UserPasswordResetRequest;

import java.util.List;
import java.util.UUID;

public interface UserService {

  UserDto registerUser(UserDto userDto);

  UserDto findUserByEmail(String email);

  List<UserDto> listAllUsers();

  String confirmEmail(String token);

  UserDto verifyUser(UserDto userDto);

  void sendVerificationEmail(String emailAddress);

  void sendPasswordResetEmail(String emailAddress);

  String resetPassword(UserPasswordResetRequest userPasswordResetRequest);

  boolean isActive(String email);

  void deleteUser(UUID uuid);

  UserDto getUser(UUID uuid);
}
