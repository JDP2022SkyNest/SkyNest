package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.request.UserEditRequest;
import com.htecgroup.skynest.model.request.UserPasswordResetRequest;
import com.htecgroup.skynest.model.request.UserRegisterRequest;
import com.htecgroup.skynest.model.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {

  UserResponse registerUser(UserRegisterRequest userRegisterRequest);

  UserDto findUserByEmail(String email);

  List<UserResponse> listAllUsers();

  String confirmEmail(String token);

  UserDto verifyUser(UserDto userDto);

  void sendVerificationEmail(String email);

  void sendPasswordResetEmail(String email);

  String resetPassword(UserPasswordResetRequest userPasswordResetRequest);

  boolean isActive(String email);

  void deleteUser(UUID uuid);

  UserResponse getUser(UUID uuid);

  UserResponse editUser(UserEditRequest userEditRequest, UUID uuid);

  void authorizeAccessToUserDetailsWith(UUID uuid);

  void authorizeAccessToAdminDetailsWith(UUID uuid);
}
