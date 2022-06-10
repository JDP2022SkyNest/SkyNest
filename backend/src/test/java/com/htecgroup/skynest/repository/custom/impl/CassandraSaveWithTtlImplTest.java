package com.htecgroup.skynest.repository.custom.impl;

import com.auth0.jwt.algorithms.Algorithm;
import com.htecgroup.skynest.model.entity.InvalidJwtEntity;
import com.htecgroup.skynest.model.entity.RoleEntity;
import com.htecgroup.skynest.util.JwtUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.cassandra.core.CassandraOperations;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CassandraSaveWithTtlImplTest {

  @Mock private CassandraOperations operations;
  @InjectMocks private CassandraSaveWithTtlImpl<InvalidJwtEntity> cassandraSaveWithTtl;

  private int validFor;
  private InvalidJwtEntity invalidJwtEntity;

  @BeforeEach
  void setUp() {
    JwtUtils.ACCESS_TOKEN_EXPIRATION_MS = 30 * 60 * 1000;
    JwtUtils.ALGORITHM = Algorithm.HMAC512("test secret");
    String token =
        JwtUtils.generate(
            "ivan.fajgelj@htecgroup.com",
            JwtUtils.ACCESS_TOKEN_EXPIRATION_MS,
            "roles",
            Collections.singletonList(RoleEntity.ROLE_WORKER));
    validFor = Math.toIntExact(JwtUtils.stillValidForInMs(token) / 1000);
    invalidJwtEntity = new InvalidJwtEntity(token);
  }

  @Test
  void save_ThrowsError_ForNullEntity() {
    Assertions.assertThrows(
        IllegalArgumentException.class, () -> cassandraSaveWithTtl.save(null, validFor));
    verify(operations, times(0)).insert(any(InvalidJwtEntity.class));
  }

  @Test
  void save_ThrowsError_ForZeroTtl() {
    Assertions.assertThrows(
        IllegalArgumentException.class, () -> cassandraSaveWithTtl.save(invalidJwtEntity, 0));
    verify(operations, times(0)).insert(any(InvalidJwtEntity.class));
  }

  @Test
  void save_ThrowsError_ForNegativeTtl() {
    Assertions.assertThrows(
        IllegalArgumentException.class, () -> cassandraSaveWithTtl.save(invalidJwtEntity, -10));
    verify(operations, times(0)).insert(any(InvalidJwtEntity.class));
  }
}
