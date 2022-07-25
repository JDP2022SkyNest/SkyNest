package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.request.TagCreateRequest;
import com.htecgroup.skynest.model.response.TagResponse;

import java.util.List;
import java.util.UUID;

public interface TagService {

  TagResponse createTag(TagCreateRequest createTagRequest);

  public List<TagResponse> listAllTags();

  void tagObject(UUID tagId, UUID objectId);

  List<TagResponse> getTagsForObject(UUID objectId);

  void untagObject(UUID tagId, UUID objectId);
}
