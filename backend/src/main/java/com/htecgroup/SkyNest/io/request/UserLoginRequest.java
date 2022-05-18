package com.htecgroup.SkyNest.io.request;

import lombok.Data;

@Data
public class UserLoginRequest {
  private String email;
  private String password;
}
