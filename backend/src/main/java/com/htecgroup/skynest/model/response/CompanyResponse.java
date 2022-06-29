package com.htecgroup.skynest.model.response;

import lombok.Data;

import java.util.UUID;

@Data
public class CompanyResponse {
  private UUID id;
  private String pib;
  private String name;
  private String address;
  private String phoneNumber;
  private String email;
  private String tierName;
}
