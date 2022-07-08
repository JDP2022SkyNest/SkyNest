package com.htecgroup.skynest.model.response;

import lombok.Data;

import java.util.UUID;

@Data
public class FileResponse {
  private UUID id;
  private String createdOn;
  private String modifiedOn;
  private String deletedOn;
  private String name;
  private String createdById;
  private String parentFolderId;
  private String bucketId;
  private String type;
  private String size;
}
