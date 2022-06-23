package com.htecgroup.skynest.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserChangePasswordRequest {

  @NotNull(message = "cannot be null")
  private String oldPassword;

  @NotNull(message = "cannot be null")
  @Pattern(
      regexp =
          "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d!@#&()–\\[{}\\]:\\-;',?|/*%~$_^+=<>\\s]{8,50}",
      message = "format not valid")
  private String newPassword;
}
