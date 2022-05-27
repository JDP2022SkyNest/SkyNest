package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.Utils;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.enitity.UserEntity;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;

  @Autowired private Utils utils;

  @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

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


}
