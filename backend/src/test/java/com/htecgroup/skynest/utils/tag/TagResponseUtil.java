package com.htecgroup.skynest.utils.tag;

import com.htecgroup.skynest.model.entity.TagEntity;
import com.htecgroup.skynest.model.response.TagResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TagResponseUtil {

    protected static final String name = "TagName";
    protected static final String rgb = "FFFFFF";

    public static TagResponse get() {
        return new TagResponse(
                UUID.fromString("d7168464-18a1-40e4-a6a1-9a9a55861092"), name, rgb);
    }
}
