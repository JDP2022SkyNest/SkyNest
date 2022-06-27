package com.htecgroup.skynest.model.io;

import com.htecgroup.skynest.util.RegexUtil;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
public class CompanyIO {
  private UUID id;

  @NotBlank(message = "cannot be null or empty")
  @Size(max = 30, message = "length cannot be over 30")
  @Pattern(regexp = RegexUtil.PIB_FORMAT_REGEX, message = "format not valid")
  private String pib;

  @NotBlank(message = "cannot be null or empty")
  @Size(max = 50, message = "length cannot be over 50")
  private String name;

  @NotBlank(message = "cannot be null or empty")
  @Size(max = 50, message = "length cannot be over 50")
  private String address;

  @NotBlank(message = "cannot be null or empty")
  @Size(max = 30, message = "length cannot be over 30")
  @Pattern(regexp = RegexUtil.PHONE_NUMBER_FORMAT_REGEX, message = "format not valid")
  private String phoneNumber;

  @NotNull(message = "cannot be null")
  @Pattern(message = "format is not valid", regexp = RegexUtil.EMAIL_FORMAT_REGEX)
  @Size(max = 254, message = "length cannot be over 254 characters")
  private String email;

  private String tierName;
}
