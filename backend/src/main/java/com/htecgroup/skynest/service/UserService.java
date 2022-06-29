package com.htecgroup.skynest.service;

import com.htecgroup.skynest.annotation.*;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.request.UserChangePasswordRequest;
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

  void deleteUser(@Valid @CurrentUserCanDelete UUID uuid);

  UserResponse getUser(@Valid @CurrentUserCanView UUID uuid);

  UserResponse editUser(@Valid @CurrentUserCanEdit UUID uuid, UserEditRequest userEditRequest);

  void enableUser(@Valid @UserNotAdmin UUID uuid);

  void changePassword(UserChangePasswordRequest userChangePasswordRequest, UUID uuid);

  void authorizeAccessForChangePassword(UUID uuid);

  UserDto findUserById(UUID uuid);

  void promoteUser(@Valid @OnlyWorkerCanBePromoted UUID userId);

  void disableUser(@Valid @UserNotAdmin UUID userId);

  void addCompanyForUser(UUID uuid, UUID companyId);
}
