package com.htecgroup.skynest.model.dto;

import com.htecgroup.skynest.model.entity.TierEntity;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CompanyDto implements Serializable {
  private UUID id;
  private LocalDateTime createdOn;
  private LocalDateTime modifiedOn;
  private LocalDateTime deletedOn;
  private String pib;
  private String name;
  private String address;
  private String phoneNumber;
  private String email;
  private TierEntity tier;
}
