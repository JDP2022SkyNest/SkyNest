package com.htecgroup.skynest.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponse {
  private String id;
  private String pib;
  private String name;
  private String address;
  private String phoneNumber;
  private String email;
  private String tierName;
}
