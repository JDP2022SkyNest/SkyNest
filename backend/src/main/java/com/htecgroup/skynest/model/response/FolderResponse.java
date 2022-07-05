package com.htecgroup.skynest.model.response;

import lombok.Data;

import java.util.UUID;

@Data
public class FolderResponse {
  private UUID id;
  private String createdOn;
  private String modifiedOn;
  private String deletedOn;
  private String name;
  private UUID createdById;
  private UUID parentFolderId;
  private UUID bucketId;
}
