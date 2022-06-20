package com.htecgroup.skynest.model.jwtObject;

import lombok.Data;

import java.util.UUID;

@Data
public class JwtObject {
  UUID uuid;
  String email;
}
