package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.exception.UserExceptionType;
import com.htecgroup.skynest.model.dto.RoleDto;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.email.Email;
import com.htecgroup.skynest.model.entity.RoleEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.model.request.UserPasswordResetRequest;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.EmailService;
import com.htecgroup.skynest.service.RoleService;
import com.htecgroup.skynest.service.UserService;
import com.htecgroup.skynest.util.EmailUtils;
import com.htecgroup.skynest.util.JwtUtils;
import com.htecgroup.skynest.util.UrlUtil;
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
  private EmailUtils emailUtils;
  private EmailService emailService;

  @Override
  public UserDto registerUser(UserDto userDto) {

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

    UserEntity userEntity = userRepository.save(modelMapper.map(userDto, UserEntity.class));

    this.sendVerificationEmail(userDto.getEmail());

    return modelMapper.map(userEntity, UserDto.class);
  }

  @Override
  public void sendVerificationEmail(String emailAddress) {
    UserDto userDto = this.findUserByEmail(emailAddress);

    if (userDto.getVerified()) {
      throw new UserException(UserExceptionType.USER_ALREADY_REGISTERED);
    }

    String token = JwtUtils.generateEmailToken(emailAddress, JwtUtils.EMAIL_VERIFICATION_PURPOSE);

    emailService.send(
        new Email(
            emailAddress,
            EmailUtils.EMAIL_VERIFICATION_SUBJECT,
            EmailUtils.EMAIL_VERIFICATION_TEMPLATE,
            UrlUtil.getEmailVerificationLink(token),
            true));
  }

  @Override
  public void sendPasswordResetEmail(String emailAddress) {
    if (!userRepository.existsByEmail(emailAddress)) {
      throw new UserException(UserExceptionType.USER_NOT_FOUND);
    }

    String token = JwtUtils.generateEmailToken(emailAddress, JwtUtils.PASSWORD_RESET_PURPOSE);

    emailService.send(
        new Email(
            emailAddress,
            EmailUtils.PASSWORD_RESET_SUBJECT,
            EmailUtils.PASSWORD_RESET_TEMPLATE,
            UrlUtil.getPasswordResetLink(token),
            true));
  }

  @Override
  public String resetPassword(UserPasswordResetRequest userPasswordResetRequest) {
    JwtUtils.validateEmailToken(
        userPasswordResetRequest.getToken(), JwtUtils.PASSWORD_RESET_PURPOSE);
    String email = JwtUtils.getEmailFromJwtEmailToken(userPasswordResetRequest.getToken());
    UserDto userDto = findUserByEmail(email);
    UserDto userDtoNewPassword =
        userDto.withEncryptedPassword(
            bCryptPasswordEncoder.encode(userPasswordResetRequest.getPassword()));
    userRepository.save(modelMapper.map(userDtoNewPassword, UserEntity.class));
    return "Password was successfully reset";
  }

  @Override
  public boolean isActive(String email) {
    UserDto userDto = findUserByEmail(email);
    return userDto.getEnabled() && userDto.getVerified() && userDto.getDeletedOn() == null;
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
  public UserDto getUser(UUID uuid) {
    UserEntity userEntity =
        userRepository
            .findById(uuid)
            .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

    return modelMapper.map(userEntity, UserDto.class);
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
  public List<UserDto> listAllUsers() {
    List<UserEntity> entityList = userRepository.findAll();
    return entityList.stream()
        .map(e -> modelMapper.map(e, UserDto.class))
        .collect(Collectors.toList());
  }

  @Override
  public String confirmEmail(String token) {
    JwtUtils.validateEmailToken(token, JwtUtils.EMAIL_VERIFICATION_PURPOSE);
    String email = JwtUtils.getEmailFromJwtEmailToken(token);
    UserDto userDto = findUserByEmail(email);
    UserDto enabledUser = this.verifyUser(userDto);
    userRepository.save(modelMapper.map(enabledUser, UserEntity.class));
    return "User verified successfully";
  }

  @Override
  public UserDto verifyUser(UserDto userDto) {
    if (isActive(userDto.getEmail())) {
      throw new UserException(UserExceptionType.USER_ALREADY_REGISTERED);
    }
    return userDto.withEnabled(true).withVerified(true);
  }
}
