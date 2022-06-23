package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.exception.UserExceptionType;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.dto.RoleDto;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.entity.RoleEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.model.request.UserChangePasswordRequest;
import com.htecgroup.skynest.model.request.UserEditRequest;
import com.htecgroup.skynest.model.request.UserRegisterRequest;
import com.htecgroup.skynest.model.response.UserResponse;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.service.RoleService;
import com.htecgroup.skynest.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private RoleService roleService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  private ModelMapper modelMapper;
  private CurrentUserService currentUserService;

  @Override
  public UserResponse registerUser(UserRegisterRequest userRegisterRequest) {

    UserDto userDto = modelMapper.map(userRegisterRequest, UserDto.class);

    if (userRepository.existsByEmail(userDto.getEmail())) {
      throw new UserException(UserExceptionType.EMAIL_ALREADY_IN_USE);
    }
    if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
      throw new UserException(UserExceptionType.PHONE_NUMBER_ALREADY_IN_USE);
    }
    String roleName = RoleEntity.ROLE_WORKER;
    RoleDto roleDto = roleService.findByName(roleName);
    userDto.setRole(roleDto);

    userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
    userDto.setVerified(false);
    userDto.setEnabled(false);
    userDto.setName(userDto.getName().trim());
    userDto.setSurname(userDto.getSurname().trim());
    userDto.setAddress(userDto.getAddress().trim());

    UserEntity userEntity = userRepository.save(modelMapper.map(userDto, UserEntity.class));

    return modelMapper.map(userEntity, UserResponse.class);
  }

  @Override
  public void deleteUser(UUID uuid) {
    if (!userRepository.existsById(uuid)) {
      throw new UserException(
          String.format("User with id %s doesn't exist", uuid), HttpStatus.NOT_FOUND);
    }
    userRepository.deleteById(uuid);
  }

  @Override
  public UserResponse getUser(UUID uuid) {

    UserEntity userEntity =
        userRepository
            .findById(uuid)
            .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

    return modelMapper.map(userEntity, UserResponse.class);
  }

  @Override
  public UserResponse editUser(UserEditRequest userEditRequest, UUID uuid) {
    UserEntity userEntity =
        userRepository
            .findById(uuid)
            .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));
    userEditRequest.setName(userEditRequest.getName().trim());
    userEditRequest.setSurname(userEditRequest.getSurname().trim());
    userEditRequest.setAddress(userEditRequest.getAddress().trim());
    modelMapper.map(userEditRequest, userEntity);
    userRepository.save(userEntity);
    return modelMapper.map(userEntity, UserResponse.class);
  }

  @Override
  public UserDto findUserByEmail(String email) {
    UserEntity userEntity =
        userRepository
            .findUserByEmail(email)
            .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

    return modelMapper.map(userEntity, UserDto.class);
  }

  @Override
  public List<UserResponse> listAllUsers() {
    List<UserEntity> entityList = userRepository.findAll();
    return entityList.stream()
        .map(e -> modelMapper.map(e, UserResponse.class))
        .collect(Collectors.toList());
  }

  public void authorizeAccessToUserDetailsWith(UUID uuid) {
    LoggedUserDto loggedUserDto = currentUserService.getLoggedUser();
    UUID loggedUserUuid = loggedUserDto.getUuid();

    if (loggedUserDto.hasRole(RoleEntity.ROLE_WORKER) && !(loggedUserUuid.equals(uuid))) {
      throw new UserException("Access denied", HttpStatus.FORBIDDEN);
    }
  }

  @Override
  public void changePassword(UserChangePasswordRequest userChangePasswordRequest, UUID uuid) {
    UserEntity userEntity =
        userRepository
            .findById(uuid)
            .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));
    if (!bCryptPasswordEncoder.matches(
        userChangePasswordRequest.getOldPassword(), userEntity.getEncryptedPassword())) {
      throw new UserException(UserExceptionType.OLD_PASSWORD_IS_INCORRECT);
    }
    String encryptedNewPassword =
        bCryptPasswordEncoder.encode(userChangePasswordRequest.getNewPassword());
    UserEntity changedPasswordEntity = userEntity.withEncryptedPassword(encryptedNewPassword);
    userRepository.save(changedPasswordEntity);
  }
}
