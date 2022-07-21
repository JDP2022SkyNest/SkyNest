package com.htecgroup.skynest.model.jwtObject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationInviteTokenData {
  String email;
  String companyName;
}
