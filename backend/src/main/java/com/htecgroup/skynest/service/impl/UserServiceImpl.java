package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.exception.UserExceptionType;
import com.htecgroup.skynest.model.dto.RoleDto;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.email.Email;
import com.htecgroup.skynest.model.entity.RoleEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.repository.RoleRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.EmailService;
import com.htecgroup.skynest.service.RoleService;
import com.htecgroup.skynest.service.UserService;
import com.htecgroup.skynest.util.JwtEmailVerificationUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private static final String SUBJECT_FOR_EMAIL_CONFIRMATION = "Confirm your email for SkyNest";
  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  private ModelMapper modelMapper;
  private RoleService roleService;
  private JwtEmailVerificationUtils jwtEmailVerificationUtils;
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
    if (userDto != null) {
      if (userDto.getEnabled() && userDto.getVerified()) {
        throw new UserException(UserExceptionType.USER_ALREADY_REGISTERED);
      }
    }

    String token = jwtEmailVerificationUtils.generateJwtEmailVerificationToken(emailAddress);

    String confirmationLink = jwtEmailVerificationUtils.getConfirmationLink() + token;
    String emailBody = jwtEmailVerificationUtils.buildEmail(emailAddress, confirmationLink);
    Email emailToSend = new Email(emailAddress, SUBJECT_FOR_EMAIL_CONFIRMATION, emailBody, true);
    emailService.send(emailToSend);
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
  public List<UserDto> listAllUsers() {
    List<UserEntity> entityList = userRepository.findAll();
    return entityList.stream()
        .map(e -> modelMapper.map(e, UserDto.class))
        .collect(Collectors.toList());
  }

  @Override
  public String confirmEmail(String token) {
    boolean validateToken = jwtEmailVerificationUtils.validateJwtToken(token);
    if (validateToken) {
      String email = jwtEmailVerificationUtils.getEmailFromJwtEmailVerificationToken(token);
      UserDto userDto = findUserByEmail(email);
      UserDto enabledUser = this.enableUser(userDto);
      userRepository.save(modelMapper.map(enabledUser, UserEntity.class));
      return "User verified successfully";
    } else throw new UserException(UserExceptionType.EMAIL_VERIFICATION_TOKEN_FAILED);
  }

  @Override
  public UserDto enableUser(UserDto userDto) {
    if (userDto.getVerified() && userDto.getEnabled()) {
      throw new UserException(UserExceptionType.USER_ALREADY_REGISTERED);
    }
    return userDto.withEnabled(true).withVerified(true);
  }
}
