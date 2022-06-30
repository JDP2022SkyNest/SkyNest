package com.htecgroup.skynest.model.response;

import lombok.Data;

import java.util.UUID;

@Data
public class BucketResponse {

  private UUID createdById;

  private String name;

  private UUID companyId;

  private String description;

  private Long size;

  private Boolean isPublic;
}
