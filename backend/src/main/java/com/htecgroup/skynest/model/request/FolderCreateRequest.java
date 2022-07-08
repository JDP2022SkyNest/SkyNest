package com.htecgroup.skynest.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FolderCreateRequest {

  @NotBlank(message = "cannot be null or empty")
  @Size(max = 1000, message = "length cannot be over 1000 characters")
  private String name;

  private UUID parentFolderId;

  @NotNull(message = "bucket id cannot be null or empty")
  private UUID bucketId;
}
