package com.htecgroup.skynest.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoveFolderToFolderRequest {
  @NotNull(message = "parent folder id cannot be null or empty")
  private UUID destinationParentFolderId;
}
