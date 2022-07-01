package com.htecgroup.skynest.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BucketResponse {

  private UUID createdById;

  private String name;

  private UUID companyId;

  private String description;

  private Long size;

  private Boolean isPublic;
}
