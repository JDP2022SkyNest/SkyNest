package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.entity.InvalidJwtEntity;

import java.util.Optional;

public interface InvalidJwtService {
  Optional<InvalidJwtEntity> invalidate(String token);

  boolean isInvalid(String token);
}
