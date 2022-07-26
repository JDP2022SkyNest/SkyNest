package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.object.ObjectNotFoundException;
import com.htecgroup.skynest.exception.tag.TagNotFoundException;
import com.htecgroup.skynest.model.entity.CompanyEntity;
import com.htecgroup.skynest.model.entity.ObjectEntity;
import com.htecgroup.skynest.model.entity.TagEntity;
import com.htecgroup.skynest.model.request.TagCreateRequest;
import com.htecgroup.skynest.model.response.TagResponse;
import com.htecgroup.skynest.repository.ObjectRepository;
import com.htecgroup.skynest.repository.ObjectToTagRepository;
import com.htecgroup.skynest.repository.TagRepository;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.service.TagValidationService;
import com.htecgroup.skynest.utils.LoggedUserDtoUtil;
import com.htecgroup.skynest.utils.ObjectEntityUtil;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

  @Mock TagRepository tagRepository;
  @Mock ObjectRepository objectRepository;
  @Mock ObjectToTagRepository objectToTagRepository;
  @Mock CurrentUserService currentUserService;
  @Mock TagValidationService tagValidationService;
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

  @Test
  void listAllTags() {
    CompanyEntity company = CompanyEntityUtil.get();
    List<TagEntity> expectedTagEntityList = Collections.singletonList(TagEntityUtil.get());

    when(currentUserService.getCompanyEntityFromLoggedUser()).thenReturn(Optional.of(company));
    when(tagRepository.findByCompanyId(any())).thenReturn(expectedTagEntityList);

    List<TagResponse> actualResponse = tagService.listAllTags();

    Assertions.assertEquals(expectedTagEntityList.size(), actualResponse.size());
    this.assertTagEntityAndTagResponse(expectedTagEntityList.get(0), actualResponse.get(0));
    verify(tagRepository, times(1)).findByCompanyId(company.getId());
  }

  @Test
  void when_tagObject_shouldThrowTagNotFound() {
    TagEntity tag = TagEntityUtil.get();
    ObjectEntity object = ObjectEntityUtil.get();

    when(tagRepository.findById(any())).thenReturn(Optional.empty());

    String expectedErrorMessage = TagNotFoundException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            TagNotFoundException.class, () -> tagService.tagObject(tag.getId(), object.getId()));

    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
    verify(tagRepository, times(1)).findById(tag.getId());
  }

  @Test
  void when_tagObject_shouldThrowObjectNotFound() {
    TagEntity tag = TagEntityUtil.get();
    ObjectEntity object = ObjectEntityUtil.get();

    when(tagRepository.findById(any())).thenReturn(Optional.of(tag));
    when(objectRepository.findById(any())).thenReturn(Optional.empty());

    String expectedErrorMessage = ObjectNotFoundException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            ObjectNotFoundException.class, () -> tagService.tagObject(tag.getId(), object.getId()));

    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
    verify(tagRepository, times(1)).findById(tag.getId());
    verify(objectRepository, times(1)).findById(object.getId());
  }

  @Test
  void when_untagObject_shouldThrowTagNotFound() {
    TagEntity tag = TagEntityUtil.get();
    ObjectEntity object = ObjectEntityUtil.get();

    when(tagRepository.findById(any())).thenReturn(Optional.empty());

    String expectedErrorMessage = TagNotFoundException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            TagNotFoundException.class, () -> tagService.untagObject(tag.getId(), object.getId()));

    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
    verify(tagRepository, times(1)).findById(tag.getId());
  }

  @Test
  void when_untagObject_shouldThrowObjectNotFound() {
    TagEntity tag = TagEntityUtil.get();
    ObjectEntity object = ObjectEntityUtil.get();

    when(tagRepository.findById(any())).thenReturn(Optional.of(tag));
    when(objectRepository.findById(any())).thenReturn(Optional.empty());

    String expectedErrorMessage = ObjectNotFoundException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            ObjectNotFoundException.class,
            () -> tagService.untagObject(tag.getId(), object.getId()));

    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
    verify(tagRepository, times(1)).findById(tag.getId());
    verify(objectRepository, times(1)).findById(object.getId());
  }

  private void assertTagEntityAndTagResponse(
      TagEntity expectedTagEntity, TagResponse actualTagResponse) {
    Assertions.assertEquals(expectedTagEntity.getId(), actualTagResponse.getId());
    Assertions.assertEquals(expectedTagEntity.getName(), actualTagResponse.getName());
    Assertions.assertEquals(expectedTagEntity.getRgb(), actualTagResponse.getRgb());
  }
}
