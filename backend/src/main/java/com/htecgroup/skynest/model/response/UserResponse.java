package com.htecgroup.skynest.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
  private String id;
  private String email;
  private String name;
  private String surname;
  private String phoneNumber;
  private String address;
  private String roleName;
  private Boolean verified;
  private Boolean enabled;
}
