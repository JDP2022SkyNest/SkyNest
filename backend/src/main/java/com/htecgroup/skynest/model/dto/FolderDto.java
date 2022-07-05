package com.htecgroup.skynest.model.dto;

import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.FolderEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
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

  private UserEntity createdBy;

  private String name;

  private FolderEntity parentFolder;

  private BucketEntity bucket;

  public FolderDto deleteFolder() {
    return this.withDeletedOn(LocalDateTime.now());
  }
}