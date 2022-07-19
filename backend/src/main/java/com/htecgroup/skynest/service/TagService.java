package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.request.TagCreateRequest;
import com.htecgroup.skynest.model.response.TagResponse;

public interface TagService {

  TagResponse createTag(TagCreateRequest createTagRequest);
}
