package com.htecgroup.skynest.model.response;

import lombok.*;

import java.util.List;
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
  private String createdByEmail;
  private UUID parentFolderId;
  private UUID bucketId;
  private String type;
  private String size;
  @With private List<TagResponse> tags;
}
