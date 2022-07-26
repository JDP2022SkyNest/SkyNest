package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.object.ObjectIsDeletedException;
import com.htecgroup.skynest.exception.tag.TagAlreadyExistsException;
import com.htecgroup.skynest.exception.tag.TagNotFromTheSameCompany;
import com.htecgroup.skynest.exception.tag.TagOnObjectAlreadyExists;
import com.htecgroup.skynest.exception.tag.TagOnObjectNotFound;
import com.htecgroup.skynest.model.entity.ObjectEntity;
import com.htecgroup.skynest.model.entity.ObjectToTagEntity;
import com.htecgroup.skynest.model.entity.TagEntity;
import com.htecgroup.skynest.model.request.TagCreateRequest;
import com.htecgroup.skynest.repository.ObjectToTagRepository;
import com.htecgroup.skynest.repository.TagRepository;
import com.htecgroup.skynest.service.TagValidationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TagValidationServiceImpl implements TagValidationService {

  private TagRepository tagRepository;
  private ObjectToTagRepository objectToTagRepository;

  @Override
  public void checkIfTagAlreadyExists(TagCreateRequest tagCreateRequest) {
    if (tagRepository.existsByName(tagCreateRequest.getName())) {
      throw new TagAlreadyExistsException();
    }
  }

  @Override
  public void checkIfTagOnObjectAlreadyExists(ObjectToTagEntity objectToTagEntity) {
    if (objectToTagRepository.existsById(objectToTagEntity.getId())) {
      throw new TagOnObjectAlreadyExists();
    }
  }

  @Override
  public void checkIfObjectIsTagged(ObjectToTagEntity objectToTagEntity) {
    if (!objectToTagRepository.existsById(objectToTagEntity.getId())) {
      throw new TagOnObjectNotFound();
    }
  }

  @Override
  public void checkIfTagAndObjectHasTheSameCompany(ObjectToTagEntity objectToTagEntity) {
    TagEntity tag = objectToTagEntity.getTag();
    ObjectEntity object = objectToTagEntity.getObject();

    if (!tag.getCompany().getId().equals(object.getCreatedBy().getCompany().getId())) {
      throw new TagNotFromTheSameCompany();
    }
  }

  @Override
  public void checkIfObjectIsNotDeleted(ObjectToTagEntity objectToTagEntity) {
    ObjectEntity object = objectToTagEntity.getObject();

    if (object.getDeletedOn() != null) {
      throw new ObjectIsDeletedException();
    }
  }
}
