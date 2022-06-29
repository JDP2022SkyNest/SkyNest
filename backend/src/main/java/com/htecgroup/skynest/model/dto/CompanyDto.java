package com.htecgroup.skynest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
  private TierDto tier;
}
