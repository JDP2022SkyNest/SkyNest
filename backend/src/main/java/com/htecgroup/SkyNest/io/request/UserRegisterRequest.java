package com.htecgroup.SkyNest.io.request;

import lombok.Data;

@Data
public class UserRegisterRequest {

  private String name;
  private String surname;
  private String email;
  private String password;
}
