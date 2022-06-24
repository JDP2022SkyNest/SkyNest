package com.htecgroup.skynest.model.request;

import com.htecgroup.skynest.util.RegexUtil;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserPasswordResetRequest {

  private String token;

  @NotNull(message = "cannot be null")
  @Pattern(regexp = RegexUtil.PASSWORD_FORMAT_REGEX, message = "format not valid")
  private String password;
}
