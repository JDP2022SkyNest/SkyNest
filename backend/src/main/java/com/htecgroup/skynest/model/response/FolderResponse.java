package com.htecgroup.skynest.model.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FolderResponse {
  private UUID id;
  private String createdOn;
  private String modifiedOn;
  private String deletedOn;
  private String name;
  private UUID createdById;
  private String createdByEmail;
  private UUID parentFolderId;
  private UUID bucketId;
  @With private List<TagResponse> tags;
}
