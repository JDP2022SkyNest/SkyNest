package com.htecgroup.skynest.model.response;

import com.htecgroup.skynest.model.entity.TagEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  private List<TagResponse> tags;
}
