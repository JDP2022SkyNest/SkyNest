package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.company.CompanyNotFoundException;
import com.htecgroup.skynest.exception.tag.TagAlreadyExistsException;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.entity.CompanyEntity;
import com.htecgroup.skynest.model.entity.TagEntity;
import com.htecgroup.skynest.model.request.TagCreateRequest;
import com.htecgroup.skynest.model.response.TagResponse;
import com.htecgroup.skynest.repository.TagRepository;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.service.TagService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class TagServiceImpl implements TagService {

  private TagRepository tagRepository;
  private CurrentUserService currentUserService;
  private ModelMapper modelMapper;

  @Override
  public TagResponse createTag(TagCreateRequest createTagRequest) {

    if (tagRepository.existsByName(createTagRequest.getName()))
      throw new TagAlreadyExistsException();

    TagEntity tagEntity = modelMapper.map(createTagRequest, TagEntity.class);

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
}
