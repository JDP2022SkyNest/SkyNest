package com.htecgroup.skynest.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserDto {

  private UUID id;
  private LocalDateTime createdOn;
  private LocalDateTime modifiedOn;
  private LocalDateTime deletedOn;
  private String email;
  private String password;
  private String encryptedPassword;
  private String name;
  private String surname;
  private String address;
  private String phoneNumber;
  private Boolean verified;
  private Boolean enabled;
  private RoleDto role;
  private CompanyDto company;
}
