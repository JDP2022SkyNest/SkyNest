package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.entity.ObjectEntity;
import com.htecgroup.skynest.model.entity.ObjectToTagKey;
import com.htecgroup.skynest.model.entity.TagEntity;
import com.htecgroup.skynest.utils.tag.TagEntityUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ObjectToTagKeyUtil {

  protected static final TagEntity tagEntity = TagEntityUtil.get();
  protected static final ObjectEntity objectEntity = ObjectEntityUtil.get();

  public static ObjectToTagKey get() {
    return new ObjectToTagKey(tagEntity.getId(), objectEntity.getId());
  }
}
