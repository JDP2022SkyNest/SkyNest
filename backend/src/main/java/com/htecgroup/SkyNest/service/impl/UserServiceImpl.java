package com.htecgroup.SkyNest.service.impl;

import com.htecgroup.SkyNest.exception.UserException;
import com.htecgroup.SkyNest.exception.UserExceptionType;
import com.htecgroup.SkyNest.model.dto.RoleDto;
import com.htecgroup.SkyNest.model.dto.UserDto;
import com.htecgroup.SkyNest.model.enitity.RoleEntity;
import com.htecgroup.SkyNest.model.enitity.UserEntity;
import com.htecgroup.SkyNest.repository.RoleRepository;
import com.htecgroup.SkyNest.repository.UserRepository;
import com.htecgroup.SkyNest.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;
  @Autowired private RoleRepository roleRepository;
  @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired private ModelMapper modelMapper;

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

    return modelMapper.map(userEntity, UserDto.class);
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
}
