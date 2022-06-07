package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.exception.UserExceptionType;
import com.htecgroup.skynest.model.dto.RoleDto;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.email.Email;
import com.htecgroup.skynest.model.entity.RoleEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.EmailService;
import com.htecgroup.skynest.service.RoleService;
import com.htecgroup.skynest.service.UserService;
import com.htecgroup.skynest.util.EmailUtils;
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

  private static final String SUBJECT_FOR_EMAIL_CONFIRMATION = "Confirm your email for SkyNest";
  private static final String SUBJECT_FOR_PASSWORD_RESET = "Password reset for SkyNest";
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

    String token = emailUtils.generateJwtEmailToken(emailAddress);

    String confirmationLink = emailUtils.getEmailConfirmationLink() + token;
    String emailBody = emailUtils.buildVerificationEmail(emailAddress, confirmationLink);
    Email emailToSend = new Email(emailAddress, SUBJECT_FOR_EMAIL_CONFIRMATION, emailBody, true);
    emailService.send(emailToSend);
  }

  @Override
  public void sendPasswordResetEmail(String email) {
    if (!userRepository.existsByEmail(email)) {
      throw new UserException(UserExceptionType.EMAIL_NOT_IN_USE);
    }

    String token = emailUtils.generateJwtEmailToken(email);

    String confirmationLink = emailUtils.getPasswordResetLink() + token;
    String emailBody = emailUtils.buildPasswordResetEmail(email, confirmationLink);
    Email emailToSend = new Email(email, SUBJECT_FOR_PASSWORD_RESET, emailBody, true);
    emailService.send(emailToSend);
  }

  @Override
  public String resetPassword(String token, String password) {
    emailUtils.validateJwtToken(token);
    String email = emailUtils.getEmailFromJwtEmailToken(token);
    UserDto userDto = findUserByEmail(email);
    UserDto userDtoNewPassword =
        userDto.withEncryptedPassword(bCryptPasswordEncoder.encode(password));
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
  public UserDto findUserByEmail(String email) {
    UserEntity userEntity =
        userRepository
            .findUserByEmail(email)
            .orElseThrow(() -> new UserException(UserExceptionType.EMAIL_NOT_IN_USE));

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
    emailUtils.validateJwtToken(token);
    String email = emailUtils.getEmailFromJwtEmailToken(token);
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
