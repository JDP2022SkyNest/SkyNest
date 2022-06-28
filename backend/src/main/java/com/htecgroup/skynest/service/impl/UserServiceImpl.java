package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.annotation.CurrentUserCanDelete;
import com.htecgroup.skynest.annotation.CurrentUserCanEdit;
import com.htecgroup.skynest.annotation.CurrentUserCanView;
import com.htecgroup.skynest.annotation.OnlyWorkerCanBePromoted;
import com.htecgroup.skynest.exception.UserNotFoundException;
import com.htecgroup.skynest.exception.WrongOldPasswordException;
import com.htecgroup.skynest.exception.auth.PasswordChangeForbiddenException;
import com.htecgroup.skynest.exception.auth.UserAlreadyDisabledException;
import com.htecgroup.skynest.exception.auth.UserAlreadyEnabledException;
import com.htecgroup.skynest.exception.auth.UserNotVerifiedException;
import com.htecgroup.skynest.exception.register.EmailAlreadyInUseException;
import com.htecgroup.skynest.exception.register.PhoneNumberAlreadyInUseException;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.dto.RoleDto;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.email.Email;
import com.htecgroup.skynest.model.entity.RoleEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.model.request.UserChangePasswordRequest;
import com.htecgroup.skynest.model.request.UserEditRequest;
import com.htecgroup.skynest.model.request.UserRegisterRequest;
import com.htecgroup.skynest.model.response.UserResponse;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.*;
import com.htecgroup.skynest.util.EmailUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Validated
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private RoleService roleService;
  private PasswordEncoderService passwordEncoderService;
  private ModelMapper modelMapper;
  private CurrentUserService currentUserService;

  private EmailService emailService;

  @Override
  public UserResponse registerUser(UserRegisterRequest userRegisterRequest) {

    UserDto userDto = modelMapper.map(userRegisterRequest, UserDto.class);

    if (userRepository.existsByEmail(userDto.getEmail())) {
      throw new EmailAlreadyInUseException();
    }
    if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
      throw new PhoneNumberAlreadyInUseException();
    }
    String roleName = RoleEntity.ROLE_WORKER;
    RoleDto roleDto = roleService.findByName(roleName);
    userDto.setRole(roleDto);

    userDto.setEncryptedPassword(passwordEncoderService.encode(userDto.getPassword()));
    userDto.setVerified(false);
    userDto.setEnabled(false);
    userDto.setName(userDto.getName().trim());
    userDto.setSurname(userDto.getSurname().trim());
    userDto.setAddress(userDto.getAddress().trim());

    UserEntity userEntity = userRepository.save(modelMapper.map(userDto, UserEntity.class));

    return modelMapper.map(userEntity, UserResponse.class);
  }

  @Override
  public void deleteUser(@Valid @CurrentUserCanDelete UUID uuid) {
    if (!userRepository.existsById(uuid)) {
      throw new UserNotFoundException();
    }
    userRepository.deleteById(uuid);
  }

  @Override
  public UserResponse getUser(@Valid @CurrentUserCanView UUID uuid) {
    UserDto userDto = findUserById(uuid);
    return modelMapper.map(userDto, UserResponse.class);
  }

  @Override
  public UserResponse editUser(
      @Valid @CurrentUserCanEdit UUID uuid, UserEditRequest userEditRequest) {
    UserEntity userEntity = userRepository.findById(uuid).orElseThrow(UserNotFoundException::new);
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
        userRepository.findUserByEmail(email).orElseThrow(UserNotFoundException::new);
    return modelMapper.map(userEntity, UserDto.class);
  }

  @Override
  public List<UserResponse> listAllUsers() {
    List<UserEntity> entityList = userRepository.findAll();
    return entityList.stream()
        .map(e -> modelMapper.map(e, UserResponse.class))
        .collect(Collectors.toList());
  }

  public void authorizeAccessForChangePassword(UUID uuid) {
    LoggedUserDto loggedUserDto = currentUserService.getLoggedUser();
    UUID loggedUserUuid = loggedUserDto.getUuid();

    if (!(loggedUserUuid.equals(uuid))) {
      throw new PasswordChangeForbiddenException();
    }
  }

  @Override
  public UserDto findUserById(UUID uuid) {
    UserEntity userEntity = userRepository.findById(uuid).orElseThrow(UserNotFoundException::new);
    return modelMapper.map(userEntity, UserDto.class);
  }

  @Override
  public void changePassword(UserChangePasswordRequest userChangePasswordRequest, UUID uuid) {
    UserDto userDto = findUserById(uuid);
    if (!passwordEncoderService.matches(
        userChangePasswordRequest.getCurrentPassword(), userDto.getEncryptedPassword())) {
      throw new WrongOldPasswordException();
    }
    String encryptedNewPassword =
        passwordEncoderService.encode(userChangePasswordRequest.getNewPassword());
    UserDto changedPasswordDto = userDto.withEncryptedPassword(encryptedNewPassword);
    userRepository.save(modelMapper.map(changedPasswordDto, UserEntity.class));

    Email email = EmailUtil.createPasswordChangeNotificationEmail(changedPasswordDto);
    emailService.send(email);
  }

  @Override
  public void disableUser(UUID userId) {
    UserDto userDto = findUserById(userId);
    if (!userDto.getVerified()) {
      throw new UserNotVerifiedException();
    }
    if (!userDto.getEnabled() && userDto.getDeletedOn() != null) {
      throw new UserAlreadyDisabledException();
    }
    UserDto disabledUserDto = userDto.disableUser();
    userRepository.save(modelMapper.map(disabledUserDto, UserEntity.class));
  }

  @Override
  public void promoteUser(@Valid @OnlyWorkerCanBePromoted UUID userId) {
    UserDto userDto = findUserById(userId);
    RoleDto userPromotedRole = roleService.findByName(RoleEntity.ROLE_MANAGER);
    UserDto changedRoleUser = userDto.withRole(userPromotedRole);
    userRepository.save(modelMapper.map(changedRoleUser, UserEntity.class));
  }

  @Override
  public void enableUser(UUID uuid) {
    UserDto userDto = findUserById(uuid);
    if (!userDto.getVerified()) {
      throw new UserNotVerifiedException();
    }
    if (userDto.getEnabled() && userDto.getDeletedOn() == null) {
      throw new UserAlreadyEnabledException();
    }
    UserDto enabledUserDto = userDto.enableUser();
    userRepository.save(modelMapper.map(enabledUserDto, UserEntity.class));
  }
}
