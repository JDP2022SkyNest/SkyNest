package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.model.entity.InvalidJwtEntity;
import com.htecgroup.skynest.repository.InvalidJwtRepository;
import com.htecgroup.skynest.service.InvalidJwtService;
import com.htecgroup.skynest.util.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class InvalidJwtServiceImpl implements InvalidJwtService {

  private InvalidJwtRepository invalidJwtRepository;

  @Override
  public Optional<InvalidJwtEntity> invalidate(String token) {

    if (token == null) return Optional.empty();

    int validFor = Math.toIntExact(JwtUtils.stillValidForInMs(token) / 1000);
    if (validFor <= 0) {
      log.warn("Can't invalidate already expired token {}", token);
      return Optional.empty();
    }
    InvalidJwtEntity invalidJwtEntity = new InvalidJwtEntity(token);

    InvalidJwtEntity savedJwtEntity = invalidJwtRepository.save(invalidJwtEntity, validFor);
    log.info("Invalidated token {}", token);

    return Optional.of(savedJwtEntity);
  }

  @Override
  public boolean isInvalid(String token) {
    return invalidJwtRepository.existsById(token);
  }
}
