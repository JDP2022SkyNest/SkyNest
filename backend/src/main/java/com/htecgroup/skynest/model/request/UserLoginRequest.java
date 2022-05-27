package com.htecgroup.skynest.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class UserLoginRequest {
  private String email;
  private String password;
}
