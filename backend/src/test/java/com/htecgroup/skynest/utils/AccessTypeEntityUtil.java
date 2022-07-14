package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.entity.AccessType;
import com.htecgroup.skynest.model.entity.AccessTypeEntity;

import java.util.UUID;

public class AccessTypeEntityUtil {

  public static final AccessTypeEntity get(AccessType accessType) {
    return new AccessTypeEntity(UUID.randomUUID(), accessType.getText());
  }
}
