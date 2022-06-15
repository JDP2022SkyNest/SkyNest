package com.htecgroup.skynest.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

  @NotNull(message = "cannot be null")
  @Pattern(
      message = "format is not valid",
      regexp =
          "[a-zA-Z0-9_+&*-]{1,64}(?:\\.[a-zA-Z0-9_+&*-]+){0,64}@(?:[a-zA-Z0-9-]+\\.){1,255}[a-zA-Z]{2,7}")
  @Size(max = 254, message = "length cannot be over 254 characters")
  private String email;

  @NotNull(message = "cannot be null")
  @Pattern(
      regexp =
          "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d!@#&()–\\[{}\\]:\\-;',?|/*%~$_^+=<>\\s]{8,50}",
      message = "format not valid")
  private String password;

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
