package com.htecgroup.skynest.service;

import com.htecgroup.skynest.annotation.CurrentUserCanEdit;
import com.htecgroup.skynest.annotation.CurrentUserCanView;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.request.UserEditRequest;
import com.htecgroup.skynest.model.request.UserRegisterRequest;
import com.htecgroup.skynest.model.response.UserResponse;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface UserService {

  UserResponse registerUser(UserRegisterRequest userRegisterRequest);

  UserDto findUserByEmail(String email);

  List<UserResponse> listAllUsers();

  void deleteUser(@Valid @CurrentUserCanEdit UUID uuid);

  UserResponse getUser(@Valid @CurrentUserCanView UUID uuid);

  UserResponse editUser(@Valid @CurrentUserCanEdit UUID uuid, UserEditRequest userEditRequest);
}
