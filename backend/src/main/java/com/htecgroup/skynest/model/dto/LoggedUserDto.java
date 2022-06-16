package com.htecgroup.skynest.model.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Getter
public class LoggedUserDto extends User {
  private UUID uuid;
  private String name;
  private String surname;
  private CompanyDto company;

  public LoggedUserDto(
      UUID uuid,
      String name,
      String surname,
      CompanyDto company,
      String email,
      String password,
      boolean enabled,
      boolean accountNonExpired,
      boolean credentialsNonExpired,
      boolean accountNonLocked,
      Collection<? extends GrantedAuthority> authorities) {
    super(
        email,
        password,
        enabled,
        accountNonExpired,
        credentialsNonExpired,
        accountNonLocked,
        authorities);
    this.uuid = uuid;
    this.name = name;
    this.surname = surname;
    this.company = company;
  }

  public boolean hasRole(String roleName) {
    String actualRole = new ArrayList<>((this.getAuthorities())).get(0).toString();
    return actualRole.equals(roleName);
  }
}
