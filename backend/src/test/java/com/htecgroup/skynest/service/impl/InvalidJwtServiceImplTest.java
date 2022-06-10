package com.htecgroup.skynest.service.impl;

import com.auth0.jwt.algorithms.Algorithm;
import com.htecgroup.skynest.model.entity.InvalidJwtEntity;
import com.htecgroup.skynest.model.entity.RoleEntity;
import com.htecgroup.skynest.repository.InvalidJwtRepository;
import com.htecgroup.skynest.util.JwtUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class InvalidJwtServiceImplTest {

  @Mock private InvalidJwtRepository invalidJwtRepository;
  @InjectMocks private InvalidJwtServiceImpl invalidJwtService;

  private String token;
  private int validFor;

  @BeforeEach
  void setUp() {
    JwtUtils.ACCESS_TOKEN_EXPIRATION_MS = 30 * 60 * 1000;
    JwtUtils.ALGORITHM = Algorithm.HMAC512("test secret");
    token =
        JwtUtils.generate(
            "ivan.fajgelj@htecgroup.com",
            JwtUtils.ACCESS_TOKEN_EXPIRATION_MS,
            "roles",
            Collections.singletonList(RoleEntity.ROLE_WORKER));
    validFor = Math.toIntExact(JwtUtils.stillValidForInMs(token) / 1000);
  }

  @Test
  void invalidate_ReturnsEntity() {

    when(invalidJwtRepository.save(any(InvalidJwtEntity.class), eq(validFor)))
        .thenReturn(new InvalidJwtEntity(token));

    Optional<InvalidJwtEntity> invalidJwtEntity = invalidJwtService.invalidate(token);

    Assertions.assertTrue(invalidJwtEntity.isPresent());
    Assertions.assertEquals(token, invalidJwtEntity.get().getToken());

    verify(invalidJwtRepository, times(1)).save(any(InvalidJwtEntity.class), eq(validFor));
  }

  @Test
  void invalidate_ReturnsEmptyOptional_ForNullToken() {

    Optional<InvalidJwtEntity> invalidJwtEntity = invalidJwtService.invalidate(null);
    Assertions.assertTrue(invalidJwtEntity.isEmpty());

    verify(invalidJwtRepository, times(0)).save(any(InvalidJwtEntity.class), anyInt());
  }

  @Test
  void invalidate_ReturnsEmptyOptional_ForExpiredToken() {
    JwtUtils.ACCESS_TOKEN_EXPIRATION_MS = 1;
    token =
        JwtUtils.generate(
            "ivan.fajgelj@htecgroup.com",
            JwtUtils.ACCESS_TOKEN_EXPIRATION_MS,
            "roles",
            Collections.singletonList(RoleEntity.ROLE_WORKER));
    validFor = Math.toIntExact(JwtUtils.stillValidForInMs(token) / 1000);

    Optional<InvalidJwtEntity> invalidJwtEntity = invalidJwtService.invalidate(null);

    Assertions.assertTrue(invalidJwtEntity.isEmpty());

    verify(invalidJwtRepository, times(0)).save(any(InvalidJwtEntity.class), anyInt());
  }

  @Test
  void invalidate_ReturnsEntity_EvenIfAlreadyInvalidated() {

    when(invalidJwtRepository.save(any(InvalidJwtEntity.class), eq(validFor)))
        .thenReturn(new InvalidJwtEntity(token));

    when(invalidJwtRepository.existsById(token)).thenReturn(false);
    Optional<InvalidJwtEntity> invalidJwtEntity = invalidJwtService.invalidate(token);
    when(invalidJwtRepository.existsById(token)).thenReturn(true);

    Assertions.assertTrue(invalidJwtEntity.isPresent());
    Assertions.assertEquals(token, invalidJwtEntity.get().getToken());
    verify(invalidJwtRepository, times(1)).save(any(InvalidJwtEntity.class), eq(validFor));

    Optional<InvalidJwtEntity> nextJwtEntity = invalidJwtService.invalidate(token);

    Assertions.assertTrue(nextJwtEntity.isPresent());
    Assertions.assertEquals(token, nextJwtEntity.get().getToken());
    verify(invalidJwtRepository, times(2)).save(any(InvalidJwtEntity.class), eq(validFor));
    verify(invalidJwtRepository, times(0)).existsById(anyString());
  }
}
