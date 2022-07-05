package com.htecgroup.skynest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolderDto {
  private UUID id;
  private LocalDateTime createdOn;

  private LocalDateTime modifiedOn;

  @With private LocalDateTime deletedOn;

  @With private UserDto createdBy;

  private String name;

  @With private FolderDto parentFolder;

  @With private BucketDto bucket;
}
