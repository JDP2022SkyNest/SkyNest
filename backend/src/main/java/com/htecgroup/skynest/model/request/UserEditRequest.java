package com.htecgroup.skynest.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class UserEditRequest {
  @NotBlank(message = "cannot be null or empty")
  @Size(max = 50, message = "length cannot be over 50")
  private String name;

  @NotBlank(message = "cannot be null or empty")
  @Size(max = 100, message = "length cannot be over 100")
  private String surname;

  @NotBlank(message = "cannot be null or empty")
  @Size(max = 30, message = "length cannot be over 30")
  @Pattern(regexp = "[\\d]+", message = "format not valid")
  private String phoneNumber;

  @NotBlank(message = "cannot be null or empty")
  @Size(max = 254, message = "length cannot be over 254")
  private String address;
}
