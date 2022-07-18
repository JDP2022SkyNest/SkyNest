package com.htecgroup.skynest.model.request;

import com.htecgroup.skynest.annotation.EmailNotInUse;
import com.htecgroup.skynest.util.RegexUtil;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegistrationInviteRequest {
  @EmailNotInUse(message = "is already in use")
  @NotNull(message = "cannot be null")
  @Pattern(message = "format is not valid", regexp = RegexUtil.EMAIL_FORMAT_REGEX)
  @Size(max = 254, message = "length cannot be over 254 characters")
  private String email;
}
