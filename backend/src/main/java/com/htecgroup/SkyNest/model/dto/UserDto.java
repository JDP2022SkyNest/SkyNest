package com.htecgroup.SkyNest.model.dto;

import lombok.Data;

@Data
public class UserDto {

  private long id;
  private String userId;
  private String name;
  private String surname;
  private String email;
  private String password;
  private String encryptedPassword;
}
