package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.model.entity.InvalidJwtEntity;
import com.htecgroup.skynest.repository.InvalidJwtRepository;
import com.htecgroup.skynest.service.InvalidJwtService;
import com.htecgroup.skynest.util.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InvalidJwtServiceImpl implements InvalidJwtService {

  private InvalidJwtRepository invalidJwtRepository;

  @Override
  public InvalidJwtEntity invalidate(String token) {
    int validFor = Math.toIntExact(JwtUtils.validFor(token));
    if (validFor > 0) {
      return invalidJwtRepository.save(new InvalidJwtEntity(token), validFor);
    } else {
      return null;
    }
  }

  @Override
  public boolean isInvalid(String token) {
    return invalidJwtRepository.existsById(token);
  }
}
