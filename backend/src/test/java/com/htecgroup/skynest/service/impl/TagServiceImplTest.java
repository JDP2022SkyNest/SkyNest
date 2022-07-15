package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.model.entity.TagEntity;
import com.htecgroup.skynest.model.request.TagCreateRequest;
import com.htecgroup.skynest.model.response.TagResponse;
import com.htecgroup.skynest.repository.TagRepository;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.utils.LoggedUserDtoUtil;
import com.htecgroup.skynest.utils.company.CompanyEntityUtil;
import com.htecgroup.skynest.utils.tag.TagCreateRequestUtil;
import com.htecgroup.skynest.utils.tag.TagEntityUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

  @Mock TagRepository tagRepository;
  @Mock CurrentUserService currentUserService;
  @Spy ModelMapper modelMapper;
  @Spy @InjectMocks TagServiceImpl tagService;

  @Test
  void createTag() {

    TagEntity expectedTagEntity = TagEntityUtil.get();
    when(currentUserService.getCompanyEntityFromLoggedUser())
        .thenReturn(Optional.of(CompanyEntityUtil.get()));
    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedWorkerUser());
    when(tagRepository.save(any())).thenReturn(expectedTagEntity);

    TagCreateRequest tagCreateRequest = TagCreateRequestUtil.get();
    TagResponse actualTagResponse = tagService.createTag(tagCreateRequest);

    this.assertTagEntityAndTagResponse(expectedTagEntity, actualTagResponse);
    verify(currentUserService, times(1)).getCompanyEntityFromLoggedUser();
    verify(currentUserService, times(1)).getLoggedUser();
  }

  private void assertTagEntityAndTagResponse(
      TagEntity expectedTagEntity, TagResponse actualTagResponse) {
    Assertions.assertEquals(expectedTagEntity.getId(), actualTagResponse.getId());
    Assertions.assertEquals(expectedTagEntity.getName(), actualTagResponse.getName());
    Assertions.assertEquals(expectedTagEntity.getRgb(), actualTagResponse.getRgb());
  }
}
