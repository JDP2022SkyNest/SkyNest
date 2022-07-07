package com.htecgroup.skynest.model.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FolderDto {
  private UUID id;
  private LocalDateTime createdOn;

  private LocalDateTime modifiedOn;

  @With private LocalDateTime deletedOn;

  @With private UserDto createdBy;

  private String name;

  @With private FolderDto parentFolder;

  @With private BucketDto bucket;

  public FolderDto deleteFolder() {
    return this.withDeletedOn(LocalDateTime.now());
  }

  public boolean isDeleted() {
    if (deletedOn != null) return true;
    else return false;
  }
}
