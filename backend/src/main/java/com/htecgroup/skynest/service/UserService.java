package com.htecgroup.skynest.service;

import com.htecgroup.skynest.annotation.*;
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

  void enableUser(@Valid @UserNotAdmin UUID uuid);

  void changePassword(UserChangePasswordRequest userChangePasswordRequest, UUID uuid);

  void authorizeAccessForChangePassword(UUID uuid);

  UserDto findUserById(UUID uuid);

  void promoteUser(
      @Valid
          @CanPromoteDemoteManagerWorker(
              role_name = RoleEntity.ROLE_WORKER,
              message = "Can't promote user that is not a worker")
          UUID uuid);

  void demoteUser(
      @Valid
          @CanPromoteDemoteManagerWorker(
              role_name = RoleEntity.ROLE_MANAGER,
              message = "Can't demote user that is not a manager")
          UUID uuid);

  void disableUser(@Valid @UserNotAdmin UUID userId);

  void addCompanyForUser(
      @Valid @UserNotInACompany @UserNotAdmin UUID uuid,
      @Valid @CurrentUserIsInCompany UUID companyId);

  void removeCompany(@Valid @AdminUserAndUserHaveSameCompany @UserNotAdmin UUID uuid);
}
