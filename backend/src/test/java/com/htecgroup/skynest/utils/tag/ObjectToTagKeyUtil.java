package com.htecgroup.skynest.utils.tag;

import com.htecgroup.skynest.model.entity.*;
import com.htecgroup.skynest.utils.ObjectEntityUtil;
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
