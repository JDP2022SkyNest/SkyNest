package com.htecgroup.skynest.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class UserPasswordResetRequest implements Serializable {

  private static final long serialVersionUID = -1093706903391027095L;
  private String token;

  @NotNull(message = "cannot be null")
  @Pattern(
      regexp =
          "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d!@#&()–\\[{}\\]:\\-;',?|/*%~$_^+=<>\\s]{8,50}",
      message = "format not valid")
  private String password;
}
