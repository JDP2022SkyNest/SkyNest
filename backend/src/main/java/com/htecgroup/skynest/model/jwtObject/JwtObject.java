package com.htecgroup.skynest.model.jwtObject;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class JwtObject {
  UUID uuid;
  String email;
}
