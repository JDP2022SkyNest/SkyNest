package com.htecgroup.skynest.utils.tag;

import com.htecgroup.skynest.model.request.TagCreateRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagCreateRequestUtil {

  protected static final String name = "TagName";
  protected static final String rgb = "FFFFFF";

  public static TagCreateRequest get() {

    return new TagCreateRequest(name, rgb);
  }
}
