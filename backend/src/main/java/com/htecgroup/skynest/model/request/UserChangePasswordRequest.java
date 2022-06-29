package com.htecgroup.skynest.model.request;

import com.htecgroup.skynest.util.RegexUtil;
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
  private String currentPassword;

  @NotNull(message = "cannot be null")
  @Pattern(regexp = RegexUtil.PASSWORD_FORMAT_REGEX, message = "format not valid")
  private String newPassword;
}
