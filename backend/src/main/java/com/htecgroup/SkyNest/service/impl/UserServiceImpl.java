package com.htecgroup.SkyNest.service.impl;

import com.htecgroup.SkyNest.Utils;
import com.htecgroup.SkyNest.model.dto.UserDto;
import com.htecgroup.SkyNest.model.enitity.UserEntity;
import com.htecgroup.SkyNest.repository.UserRepository;
import com.htecgroup.SkyNest.service.EmailService;
import com.htecgroup.SkyNest.service.UserService;
import com.htecgroup.SkyNest.util.JwtEmailVerificationUtils;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;

  @Autowired private JwtEmailVerificationUtils jwtEmailVerificationUtils;
  @Autowired private Utils utils;

  @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired private EmailService emailService;

  @Override
  public String registerUser(UserDto userDto) {

    if (userRepository.findUserByEmail(userDto.getEmail()) != null) {
      throw new RuntimeException("Email already in use");
    }

    userDto.setUserId(utils.generateUserId(30));
    userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

    ModelMapper modelMapper = new ModelMapper();
    UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

    userEntity = userRepository.save(userEntity);

    String token = jwtEmailVerificationUtils.generateJwtEmailVerificationToken(userEntity);

    // TODO: SEND EMAIL

    // for testing
    return token;
    // return modelMapper.map(userEntity, UserDto.class);
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
  public String confirmEmail(String token) {
    boolean validated = jwtEmailVerificationUtils.validateJwtToken(token);
    String email = jwtEmailVerificationUtils.getEmailFromJwtEmailVerificationToken(token);

    String isUserEnabled;

    // TODO: Exception Handling
    if (validated) {
      isUserEnabled = this.verifyUser(email);
    } else
      throw new RuntimeException(
          "Jwt Token completed all validations with no errors and is still invalid");

    return isUserEnabled;
  }

  public String verifyUser(String email) {
    // TODO: possibly in different service
    return "User successfully verified";
  }
}
