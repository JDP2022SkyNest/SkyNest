package com.htecgroup.skynest.model.response;

import com.htecgroup.skynest.model.dto.CompanyDto;
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
  private String username;
  private String name;
  private String surname;
  private CompanyDto company;
  @With private List<String> roles;
}
