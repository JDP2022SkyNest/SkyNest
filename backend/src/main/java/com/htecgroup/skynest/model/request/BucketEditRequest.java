package com.htecgroup.skynest.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BucketEditRequest {

  @NotBlank(message = "cannot be null or empty")
  @Size(max = 1000, message = "length cannot be over 1000 characters")
  private String name;

  @NotBlank(message = "cannot be null or empty")
  @Size(max = 5000, message = "length cannot be over 5000 characters")
  private String description;

  @NotBlank(message = "cannot be null or empty")
  @Pattern(regexp = "true|false", message = "must be true or false")
  private String isPublic;
}
