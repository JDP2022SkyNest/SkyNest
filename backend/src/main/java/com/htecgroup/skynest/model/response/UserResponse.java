package com.htecgroup.skynest.model.response;

import lombok.Data;

@Data
public class UserResponse {
  private String id;
  private String email;
  private String name;
  private String surname;
  private String phoneNumber;
  private String address;
}
