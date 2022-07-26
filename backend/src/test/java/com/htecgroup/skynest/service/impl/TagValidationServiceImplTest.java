package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.object.ObjectIsDeletedException;
import com.htecgroup.skynest.exception.tag.TagAlreadyExistsException;
import com.htecgroup.skynest.exception.tag.TagNotFromTheSameCompany;
import com.htecgroup.skynest.exception.tag.TagOnObjectAlreadyExists;
import com.htecgroup.skynest.exception.tag.TagOnObjectNotFound;
import com.htecgroup.skynest.model.entity.ObjectEntity;
import com.htecgroup.skynest.model.entity.ObjectToTagEntity;
import com.htecgroup.skynest.model.entity.ObjectToTagKey;
import com.htecgroup.skynest.model.entity.TagEntity;
import com.htecgroup.skynest.model.request.TagCreateRequest;
import com.htecgroup.skynest.repository.ObjectToTagRepository;
import com.htecgroup.skynest.repository.TagRepository;
import com.htecgroup.skynest.utils.ObjectEntityUtil;
import com.htecgroup.skynest.utils.tag.ObjectToTagKeyUtil;
import com.htecgroup.skynest.utils.tag.TagCreateRequestUtil;
import com.htecgroup.skynest.utils.tag.TagEntityUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagValidationServiceImplTest {
  @Mock TagRepository tagRepository;
  @Mock ObjectToTagRepository objectToTagRepository;
  @InjectMocks TagValidationServiceImpl tagValidationService;

  @Test
  void checkIfTagAlreadyExists() {
    TagCreateRequest tagCreateRequest = TagCreateRequestUtil.get();
    when(tagRepository.existsByName(any())).thenReturn(true);
    String expectedErrorMessage = TagAlreadyExistsException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            TagAlreadyExistsException.class,
            () -> tagValidationService.checkIfTagAlreadyExists(tagCreateRequest));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
    verify(tagRepository, times(1)).existsByName(tagCreateRequest.getName());
  }

  @Test
  void checkIfTagOnObjectAlreadyExists() {
    TagEntity tag = TagEntityUtil.get();
    ObjectEntity object = ObjectEntityUtil.get();
    ObjectToTagKey key = ObjectToTagKeyUtil.get();
    ObjectToTagEntity objectToTagEntity = new ObjectToTagEntity(key, tag, object);

    when(objectToTagRepository.existsById(any())).thenReturn(true);

    String expectedErrorMessage = TagOnObjectAlreadyExists.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            TagOnObjectAlreadyExists.class,
            () -> tagValidationService.checkIfTagOnObjectAlreadyExists(objectToTagEntity));

    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
    verify(objectToTagRepository, times(1)).existsById(key);
  }

  @Test
  void checkIfObjectIsTagged() {
    TagEntity tag = TagEntityUtil.get();
    ObjectEntity object = ObjectEntityUtil.get();
    ObjectToTagKey key = ObjectToTagKeyUtil.get();
    ObjectToTagEntity objectToTagEntity = new ObjectToTagEntity(key, tag, object);

    when(objectToTagRepository.existsById(any())).thenReturn(false);

    String expectedErrorMessage = TagOnObjectNotFound.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            TagOnObjectNotFound.class,
            () -> tagValidationService.checkIfObjectIsTagged(objectToTagEntity));

    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
    verify(objectToTagRepository, times(1)).existsById(key);
  }

  @Test
  void checkIfTagAndObjectHasTheSameCompany() {
    TagEntity tag = TagEntityUtil.getOtherCompanyTag();
    ObjectEntity object = ObjectEntityUtil.get();
    ObjectToTagKey key = ObjectToTagKeyUtil.get();
    ObjectToTagEntity objectToTagEntity = new ObjectToTagEntity(key, tag, object);

    String expectedErrorMessage = TagNotFromTheSameCompany.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            TagNotFromTheSameCompany.class,
            () -> tagValidationService.checkIfTagAndObjectHasTheSameCompany(objectToTagEntity));

    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void checkIfObjectIsNotDeleted() {
    TagEntity tag = TagEntityUtil.get();
    ObjectEntity object = ObjectEntityUtil.getDeleted();
    ObjectToTagKey key = ObjectToTagKeyUtil.get();
    ObjectToTagEntity objectToTagEntity = new ObjectToTagEntity(key, tag, object);

    String expectedErrorMessage = ObjectIsDeletedException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            ObjectIsDeletedException.class,
            () -> tagValidationService.checkIfObjectIsNotDeleted(objectToTagEntity));

    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }
}
