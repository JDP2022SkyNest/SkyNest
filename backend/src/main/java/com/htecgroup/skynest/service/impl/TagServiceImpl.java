package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.company.CompanyNotFoundException;
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

@Service
@AllArgsConstructor
@Log4j2
public class TagServiceImpl implements TagService {

  private TagRepository tagRepository;
  private CurrentUserService currentUserService;
  private ModelMapper modelMapper;

  @Override
  public TagResponse createTag(TagCreateRequest createTagRequest) {
    TagEntity tagEntity = modelMapper.map(createTagRequest, TagEntity.class);

    CompanyEntity currentUsersCompany =
        currentUserService
            .getCompanyEntityFromLoggedUser()
            .orElseThrow(CompanyNotFoundException::new);

    tagEntity.setName(tagEntity.getName().trim());
    tagEntity.setCompany(currentUsersCompany);
    tagEntity.setRgb(tagEntity.getRgb().substring(1));

    TagEntity savedTagEntity = tagRepository.save(tagEntity);

    log.info(
        "User {} ({}) has created tag {} ({})",
        currentUserService.getLoggedUser().getUsername(),
        currentUserService.getLoggedUser().getUuid(),
        savedTagEntity.getName(),
        savedTagEntity.getId());

    return modelMapper.map(savedTagEntity, TagResponse.class);
  }
}
