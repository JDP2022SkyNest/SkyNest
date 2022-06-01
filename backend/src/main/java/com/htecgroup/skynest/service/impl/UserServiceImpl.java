package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.exception.UserExceptionType;
import com.htecgroup.skynest.model.dto.RoleDto;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.enitity.RoleEntity;
import com.htecgroup.skynest.model.enitity.UserEntity;
import com.htecgroup.skynest.repository.RoleRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.EmailService;
import com.htecgroup.skynest.service.UserService;
import com.htecgroup.skynest.util.JwtEmailVerificationUtils;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
  private UserRepository userRepository;
  private JwtEmailVerificationUtils jwtEmailVerificationUtils;
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  private ModelMapper modelMapper;
  private EmailService emailService;
  private RoleRepository roleRepository;

  @Override
  public UserDto registerUser(UserDto userDto) {

    if (userRepository.existsByEmail(userDto.getEmail())) {
      throw new UserException(UserExceptionType.EMAIL_ALREADY_IN_USE);
    }

    RoleEntity roleEntity =
        roleRepository
            .findByName(RoleEntity.ROLE_WORKER)
            .orElseThrow(
                () ->
                    new UserException(
                        "Role " + RoleEntity.ROLE_WORKER + " not found.",
                        HttpStatus.INTERNAL_SERVER_ERROR));
    userDto.setRole(modelMapper.map(roleEntity, RoleDto.class));

    userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
    userDto.setVerified(false);
    userDto.setEnabled(false);

    UserEntity userEntity = userRepository.save(modelMapper.map(userDto, UserEntity.class));

    this.sendVerificationEmail(userDto.getEmail());

    return modelMapper.map(userEntity, UserDto.class);
  }

  @Override
  public void sendVerificationEmail(String email) {
    UserDto userDto = this.findUserByEmail(email);
    if (userDto != null) {
      if (userDto.getEnabled() && userDto.getVerified()) {
        throw new UserException(UserExceptionType.USER_ALREADY_REGISTERED);
      }
    }

    String token = jwtEmailVerificationUtils.generateJwtEmailVerificationToken(email);

    String confirmationLink = jwtEmailVerificationUtils.getConfirmationLink() + token;
    String subject = "Confirm your email for SkyNest";
    emailService.send(email, jwtEmailVerificationUtils.buildEmail(email, confirmationLink), subject);
  }

  @Override
  public UserDto findUserByEmail(String email) {
    UserEntity userEntity =
        userRepository
            .findUserByEmail(email)
            .orElseThrow(
                () -> new UsernameNotFoundException("could not find user with email: " + email));

    return modelMapper.map(userEntity, UserDto.class);
  }

  @Override
  public String confirmEmail(String token) {
    boolean validateToken = jwtEmailVerificationUtils.validateJwtToken(token);
    if (validateToken) {
      String email = jwtEmailVerificationUtils.getEmailFromJwtEmailVerificationToken(token);
      UserDto userDto = findUserByEmail(email);
      userDto = this.enableUser(userDto);
      userRepository.save(modelMapper.map(userDto, UserEntity.class));
      return "User verified successfully";
    } else throw new UserException(UserExceptionType.EMAIL_VERIFICATION_TOKEN_FAILED);
  }

  @Override
  public UserDto enableUser(UserDto userDto){
    if (userDto.getVerified() && userDto.getEnabled()) {
      throw new UserException(UserExceptionType.USER_ALREADY_REGISTERED);
    }
    userDto.setEnabled(true);
    userDto.setVerified(true);
    return userDto;
  }
}
