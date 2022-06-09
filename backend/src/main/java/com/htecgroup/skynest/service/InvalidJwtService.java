package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.entity.InvalidJwtEntity;

public interface InvalidJwtService {
  InvalidJwtEntity invalidate(String token);

  boolean isInvalid(String token);
}
