package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.dto.RoleDto;
import com.htecgroup.skynest.model.entity.RoleEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class BasicUtil {

  protected static final LocalDateTime currentDateTime = LocalDateTime.now();
  protected static final RoleEntity roleWorkerEntity =
      new RoleEntity(UUID.randomUUID(), RoleEntity.ROLE_WORKER);
  protected static final RoleDto roleWorkerDto =
      new RoleDto(UUID.randomUUID(), RoleEntity.ROLE_WORKER);
  protected static final RoleEntity roleAdminEntity =
      new RoleEntity(UUID.randomUUID(), RoleEntity.ROLE_ADMIN);
  protected static final RoleDto roleAdminDto =
      new RoleDto(UUID.randomUUID(), RoleEntity.ROLE_ADMIN);
  protected static String email = "test@test.com";
  protected static String password = "password";
  protected static String encryptedPassoword =
      "$2a$12$R9h/cIPz0gi.URNNX3kh2OPST9/PgBkqquzi.Ss7KIUgO2t0jWMUW";
  protected static String name = "Name";
  protected static String surname = "Surname";
  protected static String address = "Address";
  protected static String phoneNumber = "381123456789";
}
