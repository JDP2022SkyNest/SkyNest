package com.htecgroup.skynest.model.request;

import lombok.Data;

@Data
public class UserRegisterRequest {

  private String email;
  private String password;
  private String name;
  private String surname;
  private String phoneNumber;
  private String address;
}
