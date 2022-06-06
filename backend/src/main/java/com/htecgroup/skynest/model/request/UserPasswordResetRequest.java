package com.htecgroup.skynest.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserPasswordResetRequest {

  private String token;

  @NotNull(message = "cannot be null")
  @Pattern(
      regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,50}",
      message = "format not valid")
  private String password;
}
