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
import com.htecgroup.skynest.service.UserService;
import com.htecgroup.skynest.util.EmailType;
import com.htecgroup.skynest.util.JwtUtils;
import com.htecgroup.skynest.util.UrlUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

  private UserService userService;
  private EmailService emailService;
  private UserRepository userRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  private ModelMapper modelMapper;

  @Override
  public String confirmEmail(String token) {
    String email = JwtUtils.getValidatedEmailVerificationTokenContext(token);
    UserDto userDto = userService.findUserByEmail(email);
    UserDto enabledUser = this.verifyUser(userDto);
    userRepository.save(modelMapper.map(enabledUser, UserEntity.class));
    return "User verified successfully";
  }

  @Override
  public String resetPassword(UserPasswordResetRequest userPasswordResetRequest) {
    String email =
        JwtUtils.getValidatedPasswordResetTokenContext(userPasswordResetRequest.getToken());
    UserDto userDto = userService.findUserByEmail(email);
    UserDto userDtoNewPassword =
        userDto.withEncryptedPassword(
            bCryptPasswordEncoder.encode(userPasswordResetRequest.getPassword()));
    userRepository.save(modelMapper.map(userDtoNewPassword, UserEntity.class));
    return "Password was successfully reset";
  }

  @Override
  public UserDto verifyUser(UserDto userDto) {
    if (userService.isActive(userDto.getEmail())) {
      throw new UserException(UserExceptionType.USER_ALREADY_REGISTERED);
    }
    return userDto.withEnabled(true).withVerified(true);
  }

  @Override
  public void sendVerificationEmail(String emailAddress) {
    UserDto userDto = userService.findUserByEmail(emailAddress);

    if (userDto.getVerified()) {
      throw new UserException(UserExceptionType.USER_ALREADY_REGISTERED);
    }

    String token = JwtUtils.generateEmailVerificationToken(emailAddress);

    Map<String, String> arguments =
        Map.of("name", userDto.getName(), "link", UrlUtil.getEmailVerificationLink(token));

    emailService.send(new Email(emailAddress, EmailType.EMAIL_VERIFICATION, arguments, true));
  }

  @Override
  public void sendPasswordResetEmail(String emailAddress) {
    UserDto userDto = userService.findUserByEmail(emailAddress);

    if (!userRepository.existsByEmail(emailAddress)) {
      throw new UserException(UserExceptionType.USER_NOT_FOUND);
    }

    String token = JwtUtils.generatePasswordResetToken(emailAddress);

    Map<String, String> arguments =
        Map.of("name", userDto.getName(), "link", UrlUtil.getPasswordResetLink(token));

    emailService.send(new Email(emailAddress, EmailType.PASSWORD_RESET, arguments, true));
  }
}
