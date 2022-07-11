package com.htecgroup.skynest.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoveFolderToBucketRequest {

  @NotNull(message = "bucket id cannot be null or empty")
  private UUID destinationBucketId;
}
