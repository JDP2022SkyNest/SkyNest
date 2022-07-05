package com.htecgroup.skynest.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoggedUserResponse {
  private UUID uuid;
  private String name;
  private String surname;
  private String positionInCompany;
  private String companyName;
  @With private List<String> roles;
}
