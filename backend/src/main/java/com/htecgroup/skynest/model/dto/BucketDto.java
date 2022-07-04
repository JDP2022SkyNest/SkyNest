package com.htecgroup.skynest.model.dto;

import com.htecgroup.skynest.model.entity.CompanyEntity;
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
public class BucketDto {

  private UUID id;
  private LocalDateTime createdOn;

  private LocalDateTime modifiedOn;

  @With private LocalDateTime deletedOn;

  private UserEntity createdBy;

  private String name;

  private CompanyEntity company;

  private String description;

  private long size;

  private Boolean isPublic;

  public BucketDto deleteBucket() {
    return this.withDeletedOn(LocalDateTime.now());
  }

  public BucketDto restoreBucket() {
    return this.withDeletedOn(null);
  }
}
