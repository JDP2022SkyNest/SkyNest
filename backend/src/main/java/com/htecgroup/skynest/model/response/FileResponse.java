package com.htecgroup.skynest.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FileResponse {
  private UUID id;
  private String createdOn;
  private String modifiedOn;
  private String deletedOn;
  private String name;
  private UUID createdById;
  private UUID parentFolderId;
  private UUID bucketId;
  private String type;
  private String size;
}
