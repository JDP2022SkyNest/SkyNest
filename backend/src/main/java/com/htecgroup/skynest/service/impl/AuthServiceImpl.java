package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.exception.UserExceptionType;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.email.Email;
import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.model.request.UserPasswordResetRequest;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.AuthService;
import com.htecgroup.skynest.service.EmailService;
import com.htecgroup.skynest.service.PasswordEncoderService;
import com.htecgroup.skynest.service.UserService;
import com.htecgroup.skynest.util.EmailUtil;
import com.htecgroup.skynest.util.JwtUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserService userService;
  private final EmailService emailService;
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final PasswordEncoderService passwordEncoderService;

  @Override
  public void sendVerificationEmail(String emailAddress) {
    UserDto userDto = userService.findUserByEmail(emailAddress);

    if (userDto.getVerified()) {
      throw new UserException(UserExceptionType.USER_ALREADY_REGISTERED);
    }

    String token = JwtUtils.generateEmailVerificationToken(emailAddress);
    Email email = EmailUtil.createVerificationEmail(userDto, token);

    emailService.send(email);
  }

  @Override
  public void sendPasswordResetEmail(String emailAddress) {
    UserDto userDto = userService.findUserByEmail(emailAddress);

    String token = JwtUtils.generatePasswordResetToken(emailAddress);
    Email email = EmailUtil.createPasswordResetEmail(userDto, token);

    emailService.send(email);
  }

  @Override
  public String resetPassword(UserPasswordResetRequest userPasswordResetRequest) {
    String email =
        JwtUtils.getValidatedPasswordResetTokenContext(userPasswordResetRequest.getToken());
    UserDto userDto = userService.findUserByEmail(email);
    UserDto userDtoNewPassword =
        userDto.withEncryptedPassword(
            passwordEncoderService.encode(userPasswordResetRequest.getPassword()));
    userRepository.save(modelMapper.map(userDtoNewPassword, UserEntity.class));
    return "Password was successfully reset";
  }

  @Override
  public String confirmEmail(String token) {
    String email = JwtUtils.getValidatedEmailVerificationTokenContext(token);
    UserDto userDto = userService.findUserByEmail(email);
    UserDto enabledUser = this.verifyUser(userDto);
    userRepository.save(modelMapper.map(enabledUser, UserEntity.class));
    return "User verified successfully";
  }

  @Override
  public boolean isActive(String email) {
    UserDto userDto = userService.findUserByEmail(email);
    return userDto.getEnabled() && userDto.getVerified() && userDto.getDeletedOn() == null;
  }

  @Override
  public UserDto verifyUser(UserDto userDto) {
    if (isActive(userDto.getEmail())) {
      throw new UserException(UserExceptionType.USER_ALREADY_REGISTERED);
    }
    return userDto.withEnabled(true).withVerified(true);
  }
}
