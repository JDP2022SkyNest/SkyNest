package com.htecgroup.skynest.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FolderCreateRequest {

  @NotBlank(message = "cannot be null or empty")
  @Size(max = 1000, message = "length cannot be over 1000 characters")
  private String name;

  @NotBlank(message = "cannot be null or empty")
  @Size(max = 1000, message = "length cannot be over 1000 characters")
  private String bucketName;

  @Size(max = 1000, message = "length cannot be over 1000 characters")
  private String parentFolderName;
}
