package com.htecgroup.SkyNest.service.impl;

import com.htecgroup.SkyNest.Utils;
import com.htecgroup.SkyNest.exception.UserException;
import com.htecgroup.SkyNest.exception.UserExceptionType;
import com.htecgroup.SkyNest.model.dto.UserDto;
import com.htecgroup.SkyNest.model.enitity.UserEntity;
import com.htecgroup.SkyNest.repository.UserRepository;
import com.htecgroup.SkyNest.service.EmailService;
import com.htecgroup.SkyNest.service.UserService;
import com.htecgroup.SkyNest.util.JwtEmailVerificationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;

  @Autowired private JwtEmailVerificationUtils jwtEmailVerificationUtils;
  @Autowired private Utils utils;

  @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired private EmailService emailService;

  @Override
  public UserDto registerUser(UserDto userDto) {

    if (userRepository.findUserByEmail(userDto.getEmail()) != null) {
      throw new RuntimeException("Email already in use");
    }

    userDto.setUserId(utils.generateUserId(30));
    userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

    ModelMapper modelMapper = new ModelMapper();
    UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

    userEntity = userRepository.save(userEntity);

    String token = jwtEmailVerificationUtils.generateJwtEmailVerificationToken(userDto);

    String confirmationLink = jwtEmailVerificationUtils.getConfirmationLink() + token;
    emailService.send(
        userEntity.getEmail(),
        jwtEmailVerificationUtils.buildEmail(userEntity.getName(), confirmationLink));

    return modelMapper.map(userEntity, UserDto.class);
  }

  @Override
  public UserDto findUserByEmail(String email) {

    UserEntity userEntity = userRepository.findUserByEmail(email);

    if (userEntity == null) {
      throw new UsernameNotFoundException("could not find user with email: " + email);
    }

    ModelMapper modelMapper = new ModelMapper();

    return modelMapper.map(userEntity, UserDto.class);
  }

  @Override
  public Boolean confirmEmail(String token) {
    boolean validateToken = jwtEmailVerificationUtils.validateJwtToken(token);
    if (validateToken) {
      String email = jwtEmailVerificationUtils.getEmailFromJwtEmailVerificationToken(token);
      return this.enableUser(email);
    } else throw new UserException(UserExceptionType.EMAIL_VERIFICATION_TOKEN_FAILED);
  }

  public Boolean enableUser(String email) {
    // TODO: possibly in different service
    return true;
  }
}
