package com.htecgroup.skynest.model.request;

import com.htecgroup.skynest.util.RegexUtil;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class TagCreateRequest {

  @NotBlank(message = "cannot be null or empty")
  @Size(max = 254, message = "length cannot be over 254")
  private String name;

  @NotBlank(message = "cannot be null or empty")
  @Pattern(regexp = RegexUtil.TAG_COLOUR_REGEX, message = "format not valid")
  private String rgb;
}
