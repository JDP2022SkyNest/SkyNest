package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.entity.ObjectEntity;
import com.htecgroup.skynest.utils.tag.TagEntityUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
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
        "Object",
        new HashSet<>(Collections.singletonList(TagEntityUtil.get())));
  }

  public static ObjectEntity getDeleted() {
    return new ObjectEntity(
        UUID.fromString("8a2ad88f-5912-48b8-b432-56b946ae3b4a"),
        null,
        null,
        LocalDateTime.now(),
        UserEntityUtil.getVerified(),
        "Object",
        new HashSet<>(Collections.singletonList(TagEntityUtil.get())));
  }
}
