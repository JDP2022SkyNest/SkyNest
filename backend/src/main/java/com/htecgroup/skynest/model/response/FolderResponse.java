package com.htecgroup.skynest.model.response;

import lombok.Data;

import java.util.UUID;

@Data
public class FolderResponse {

  private UUID createdById;

  private String name;

  private UUID bucketId;

  private UUID parentFolderId;
}
