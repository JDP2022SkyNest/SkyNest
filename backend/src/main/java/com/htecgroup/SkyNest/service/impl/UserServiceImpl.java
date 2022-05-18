package com.htecgroup.SkyNest.service.impl;

import com.htecgroup.SkyNest.Utils;
import com.htecgroup.SkyNest.dto.UserDto;
import com.htecgroup.SkyNest.enitity.UserEntity;
import com.htecgroup.SkyNest.repository.UserRepository;
import com.htecgroup.SkyNest.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    UserEntity userEntity = new UserEntity();

    userDto.setUserId(utils.generateUserId(30));
    userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
    BeanUtils.copyProperties(userDto, userEntity);

    userEntity = userRepository.save(userEntity);
    BeanUtils.copyProperties(userEntity, userDto);

    return userDto;
  }
}
