package com.htecgroup.skynest.model.response;

import lombok.Data;

@Data
public class FileResponse {
  private String id;
  private String name;
  private String type;
  private String size;
  private String parentFolderId;
  private String bucketId;
  private String createdById;
}
