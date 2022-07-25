package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.company.CompanyNotFoundException;
import com.htecgroup.skynest.exception.object.ObjectNotFoundException;
import com.htecgroup.skynest.exception.tag.*;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.entity.*;
import com.htecgroup.skynest.model.request.TagCreateRequest;
import com.htecgroup.skynest.model.response.TagResponse;
import com.htecgroup.skynest.repository.ObjectRepository;
import com.htecgroup.skynest.repository.ObjectToTagRepository;
import com.htecgroup.skynest.model.entity.CompanyEntity;
import com.htecgroup.skynest.model.entity.TagEntity;
import com.htecgroup.skynest.repository.TagRepository;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.service.TagService;
import com.htecgroup.skynest.service.TagValidationService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@Service
@AllArgsConstructor
@Log4j2
public class TagServiceImpl implements TagService {

  private TagRepository tagRepository;
  private CurrentUserService currentUserService;
  private ObjectRepository objectRepository;
  private ObjectToTagRepository objectToTagRepository;
  private TagValidationService tagValidationService;
  private ModelMapper modelMapper;

  @Override
  public TagResponse createTag(TagCreateRequest tagCreateRequest) {

    tagValidationService.checkIfTagAlreadyExists(tagCreateRequest);
    TagEntity tagEntity = modelMapper.map(tagCreateRequest, TagEntity.class);

    LoggedUserDto loggedUserDto = currentUserService.getLoggedUser();

    CompanyEntity currentUsersCompany =
        currentUserService
            .getCompanyEntityFromLoggedUser()
            .orElseThrow(CompanyNotFoundException::new);

    tagEntity.setName(tagEntity.getName().trim());
    tagEntity.setCompany(currentUsersCompany);

    TagEntity savedTagEntity = tagRepository.save(tagEntity);

    log.info(
        "User {} ({}) has created tag {} ({})",
        loggedUserDto.getUsername(),
        loggedUserDto.getUuid(),
        savedTagEntity.getName(),
        savedTagEntity.getId());

    return modelMapper.map(savedTagEntity, TagResponse.class);
  }

  @Override
  public List<TagResponse> listAllTags() {
    CompanyEntity companyEntity =
        currentUserService
            .getCompanyEntityFromLoggedUser()
            .orElseThrow(CompanyNotFoundException::new);

    return tagRepository.findByCompanyId(companyEntity.getId()).stream()
        .map(e -> modelMapper.map(e, TagResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  public void tagObject(UUID tagId, UUID objectId) {
    TagEntity tagEntity = tagRepository.findById(tagId).orElseThrow(TagNotFoundException::new);
    ObjectEntity objectEntity =
        objectRepository.findById(objectId).orElseThrow(ObjectNotFoundException::new);

    ObjectToTagKey key = new ObjectToTagKey(tagId, objectId);
    ObjectToTagEntity objectToTagEntity = new ObjectToTagEntity(key, tagEntity, objectEntity);

    tagValidationService.checkIfTagOnObjectAlreadyExists(objectToTagEntity);
    tagValidationService.checkIfTagAndObjectHasTheSameCompany(objectToTagEntity);
    tagValidationService.checkIfObjectIsNotDeleted(objectToTagEntity);

    objectToTagRepository.save(objectToTagEntity);

    LoggedUserDto loggedUserDto = currentUserService.getLoggedUser();
    log.info(
        "User {} ({}) added tag {} ({}) for object {} ({})",
        loggedUserDto.getUsername(),
        loggedUserDto.getUuid(),
        tagEntity.getName(),
        tagEntity.getId(),
        objectEntity.getName(),
        objectEntity.getId());
  }

  @Override
  public List<TagResponse> getTagsForObject(UUID objectId) {

    List<ObjectToTagEntity> pairs = objectToTagRepository.findAllByIdObjectId(objectId);

    return pairs.stream()
        .map(e -> tagRepository.findById(e.getTag().getId()).orElseThrow(TagNotFoundException::new))
        .map(e -> modelMapper.map(e, TagResponse.class))
        .collect(Collectors.toList());
  }

    @Override
    public void untagObject(UUID tagId, UUID objectId) {

      TagEntity tagEntity = tagRepository.findById(tagId).orElseThrow(TagNotFoundException::new);
      ObjectEntity objectEntity =
              objectRepository.findById(objectId).orElseThrow(ObjectNotFoundException::new);

      ObjectToTagKey key = new ObjectToTagKey(tagId, objectId);
      ObjectToTagEntity objectToTagEntity = new ObjectToTagEntity(key, tagEntity, objectEntity);

      tagValidationService.checkIfObjectIsTagged(objectToTagEntity);
      tagValidationService.checkIfTagAndObjectHasTheSameCompany(objectToTagEntity);
      tagValidationService.checkIfObjectIsNotDeleted(objectToTagEntity);

      objectToTagRepository.delete(objectToTagEntity);

      LoggedUserDto loggedUserDto = currentUserService.getLoggedUser();
      log.info(
              "User {} ({}) removed tag {} ({}) from object {} ({})",
              loggedUserDto.getUsername(),
              loggedUserDto.getUuid(),
              tagEntity.getName(),
              tagEntity.getId(),
              objectEntity.getName(),
              objectEntity.getId());
    }
}
