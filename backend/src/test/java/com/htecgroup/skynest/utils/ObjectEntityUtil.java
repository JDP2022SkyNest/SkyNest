package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.entity.ObjectEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ObjectEntityUtil {

  public static ObjectEntity get() {
    return new ObjectEntity(
        UUID.fromString("8a2ad88f-5912-48b8-b432-56b946ae3b4a"),
        null,
        null,
        null,
        UserEntityUtil.getVerified(),
        "Object");
  }
}
