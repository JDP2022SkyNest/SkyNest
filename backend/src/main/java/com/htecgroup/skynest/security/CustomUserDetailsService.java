package com.htecgroup.skynest.security;

import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDto userDto = userService.findUserByEmail(username);

    List<SimpleGrantedAuthority> authorities =
        Collections.singletonList(new SimpleGrantedAuthority(userDto.getRole().getName()));

    return new LoggedUserDto(
        userDto.getId(),
        userDto.getName(),
        userDto.getSurname(),
        userDto.getCompany(),
        userDto.getEmail(),
        userDto.getPassword(),
        userDto.getEnabled(),
        true,
        true,
        true,
        authorities);
  }
}
