package com.htecgroup.skynest.utils.tag;

import com.htecgroup.skynest.model.entity.CompanyEntity;
import com.htecgroup.skynest.model.entity.TagEntity;
import com.htecgroup.skynest.utils.company.CompanyEntityUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TagEntityUtil {

  protected static final CompanyEntity company = CompanyEntityUtil.get();
  protected static final String name = "TagName";
  protected static final String rgb = "FFFFFF";

  public static TagEntity get() {
    return new TagEntity(
        UUID.fromString("d7168464-18a1-40e4-a6a1-9a9a55861092"), company, name, rgb);
  }

  public static TagEntity getOtherCompanyTag() {
    return new TagEntity(
        UUID.fromString("d7168464-18a1-40e4-a6a1-9a9a55861092"), new CompanyEntity(), name, rgb);
  }
}
