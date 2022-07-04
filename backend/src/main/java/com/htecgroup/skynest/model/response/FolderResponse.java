package com.htecgroup.skynest.model.response;

import lombok.Data;

import java.util.UUID;

@Data
public class FolderResponse {
  private UUID id;
  private String name;
  private String createdById;
  private String parentFolderId;
  private String bucketId;
}
