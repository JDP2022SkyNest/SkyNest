package com.htecgroup.skynest.security;

import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UserDto userDto = userService.findUserByEmail(username);

    return new User(userDto.getEmail(), userDto.getEncryptedPassword(), new ArrayList<>());
  }
}
