package com.htecgroup.skynest.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BucketResponse {

  private UUID bucketId;

  private UUID createdById;

  private String name;

  private UUID companyId;

  private String description;

  private Long size;

  private Boolean isPublic;

  private String deletedOn;

  @With private List<TagResponse> tags;
}
