package com.htecgroup.skynest.model.response;

import com.htecgroup.skynest.model.entity.CompanyEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
import lombok.Data;

@Data
public class BucketResponse {
  private UserEntity createdBy;

  private String name;

  private CompanyEntity company;

  private String description;

  private long size;
}
