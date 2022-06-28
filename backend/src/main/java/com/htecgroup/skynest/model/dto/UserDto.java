package com.htecgroup.skynest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
  private UUID id;
  private LocalDateTime createdOn;
  private LocalDateTime modifiedOn;
  @With private LocalDateTime deletedOn;
  private String email;
  private String password;
  @With private String encryptedPassword;
  private String name;
  private String surname;
  private String address;
  private String phoneNumber;
  @With private Boolean verified;
  @With private Boolean enabled;
  private RoleDto role;
  private CompanyDto company;

  public UserDto enableUser() {
    return this.withEnabled(true).withDeletedOn(null);
  }

  public UserDto disableUser() {
    return this.withEnabled(false).withDeletedOn(LocalDateTime.now());
  }
}
