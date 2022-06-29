package com.htecgroup.skynest.service;

import com.htecgroup.skynest.annotation.CanPromoteDemoteManagerWorker;
import com.htecgroup.skynest.annotation.CurrentUserCanDelete;
import com.htecgroup.skynest.annotation.CurrentUserCanEdit;
import com.htecgroup.skynest.annotation.CurrentUserCanView;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.entity.RoleEntity;
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

  void enableUser(UUID uuid);

  void changePassword(UserChangePasswordRequest userChangePasswordRequest, UUID uuid);

  void authorizeAccessForChangePassword(UUID uuid);

  UserDto findUserById(UUID uuid);

  void promoteUser(
      @Valid
          @CanPromoteDemoteManagerWorker(
              role_name = RoleEntity.ROLE_WORKER,
              message = "Can't promote user that is not a worker")
          UUID uuid);

  void disableUser(UUID uuid);

  void demoteUser(
      @Valid
          @CanPromoteDemoteManagerWorker(
              role_name = RoleEntity.ROLE_MANAGER,
              message = "Can't demote user that is not a manager")
          UUID uuid);
}
