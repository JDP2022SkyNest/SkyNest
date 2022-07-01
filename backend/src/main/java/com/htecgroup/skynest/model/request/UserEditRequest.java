package com.htecgroup.skynest.model.request;

import com.htecgroup.skynest.util.RegexUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEditRequest {

  @NotBlank(message = "cannot be null or empty")
  @Size(max = 50, message = "length cannot be over 50")
  @Pattern(regexp = RegexUtil.USER_NAME_AND_SURNAME_REGEX, message = "format not valid")
  private String name;

  @NotBlank(message = "cannot be null or empty")
  @Size(max = 100, message = "length cannot be over 100")
  @Pattern(regexp = RegexUtil.USER_NAME_AND_SURNAME_REGEX, message = "format not valid")
  private String surname;

  @NotBlank(message = "cannot be null or empty")
  @Size(max = 30, message = "length cannot be over 30")
  @Pattern(regexp = RegexUtil.PHONE_NUMBER_FORMAT_REGEX, message = "format not valid")
  private String phoneNumber;

  @NotBlank(message = "cannot be null or empty")
  @Size(max = 254, message = "length cannot be over 254")
  private String address;

  @NotBlank(message = "cannot be null or empty")
  @Size(max = 255, message = "length cannot be over 255")
  private String positionInCompany;
}
