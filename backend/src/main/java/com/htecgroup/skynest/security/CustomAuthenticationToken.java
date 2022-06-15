package com.htecgroup.skynest.security;

import com.htecgroup.skynest.model.dto.LoggedUserDto;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {

  private LoggedUserDto loggedUserDto;

  /**
   * Creates a token with the supplied array of authorities.
   *
   * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal represented
   *     by this authentication object.
   */
  public CustomAuthenticationToken(
      LoggedUserDto loggedUserDto, Collection<? extends GrantedAuthority> authorities) {
    super(loggedUserDto.getUsername(), loggedUserDto.getPassword(), authorities);
    this.loggedUserDto = loggedUserDto;
  }

  @Override
  public Object getPrincipal() {
    return loggedUserDto;
  }
}
