package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.FolderEntity;
import com.htecgroup.skynest.model.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class FolderBuilder {
  UUID getId;
  LocalDateTime getCreatedOn;

  LocalDateTime getModifiedOn;

  LocalDateTime getDeletedOn;

  UserEntity getCreatedBy;

  String getName;

  FolderEntity getParentFolder;

  BucketEntity getBucket;
}
