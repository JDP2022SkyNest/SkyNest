package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.request.TagCreateRequest;
import com.htecgroup.skynest.model.response.TagResponse;

import java.util.UUID;

public interface TagService {

  TagResponse createTag(TagCreateRequest createTagRequest);

  void tagObject(UUID tagId, UUID objectId);
}
