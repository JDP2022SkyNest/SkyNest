package com.htecgroup.skynest.model.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserResponse implements Serializable {
  private static final long serialVersionUID = 5751953598376992303L;
  private String id;
  private String email;
  private String name;
  private String surname;
  private String phoneNumber;
  private String address;
}
