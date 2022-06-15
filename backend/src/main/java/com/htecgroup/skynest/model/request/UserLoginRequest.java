package com.htecgroup.skynest.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {
  private static final long serialVersionUID = 4476366081697205464L;
  private String email;
  private String password;
}
