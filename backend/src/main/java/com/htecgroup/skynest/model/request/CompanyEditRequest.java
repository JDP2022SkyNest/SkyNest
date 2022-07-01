package com.htecgroup.skynest.model.request;

import com.htecgroup.skynest.util.RegexUtil;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CompanyEditRequest {

  @NotBlank(message = "cannot be null or empty")
  @Size(max = 50, message = "length cannot be over 50")
  private String name;

  @NotBlank(message = "cannot be null or empty")
  @Size(max = 254, message = "length cannot be over 50")
  private String address;

  @NotBlank(message = "cannot be null or empty")
  @Size(max = 30, message = "length cannot be over 30")
  @Pattern(regexp = RegexUtil.PHONE_NUMBER_FORMAT_REGEX, message = "format not valid")
  private String phoneNumber;
}
