package com.htecgroup.skynest.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionResponse {

  private UUID grantedToId;
  private String grantedToEmail;

  private UUID objectId;

  private String grantedOn;

  private String accessName;

  private UUID grantedById;
  private String grantedByEmail;
}
