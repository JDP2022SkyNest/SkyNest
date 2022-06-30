package com.htecgroup.skynest.model.dto;

import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.FolderEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
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

  private UserEntity createdBy;

  private String name;

  private FolderEntity parentFolder;

  private BucketEntity bucket;
}
