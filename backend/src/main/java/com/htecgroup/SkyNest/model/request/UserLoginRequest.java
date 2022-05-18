package com.htecgroup.SkyNest.model.request;

import lombok.Data;

@Data
public class UserLoginRequest {
  private String email;
  private String password;
}
