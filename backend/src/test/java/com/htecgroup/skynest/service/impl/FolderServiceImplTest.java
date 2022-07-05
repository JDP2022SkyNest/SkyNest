package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.folder.FolderAlreadyDeletedException;
import com.htecgroup.skynest.exception.folder.FolderNotFoundException;
import com.htecgroup.skynest.model.entity.FolderEntity;
import com.htecgroup.skynest.model.response.FolderResponse;
import com.htecgroup.skynest.repository.FolderRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.ActionService;
import com.htecgroup.skynest.service.BucketService;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.utils.FolderEntityUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FolderServiceImplTest {

  @Spy @InjectMocks FolderServiceImpl folderService;
  @Mock private FolderRepository folderRepository;
  @Mock private BucketService bucketService;
  @Mock private CurrentUserService currentUserService;
  @Mock private UserRepository userRepository;
  @Spy private ModelMapper modelMapper;
  @Mock private ActionService actionService;
  @Captor private ArgumentCaptor<FolderEntity> captorFolderEntity;

  @Test
  void when_AlreadyDeletedFolder_deletedFolder_ShouldThrowFolderAlreadyDeleted() {
    FolderEntity folderEntity = FolderEntityUtil.getDeletedFolder();
    doReturn(folderEntity).when(folderRepository).findFolderById(any());
    String expectedErrorMessage = FolderAlreadyDeletedException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            FolderAlreadyDeletedException.class,
            () -> folderService.removeFolder(UUID.randomUUID()));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void when_deleteFolder_ShouldDeleteFolder() {
    FolderEntity folderEntity = FolderEntityUtil.getFolderWithParent();
    doReturn(folderEntity).when(folderRepository).findFolderById(any());

    folderService.removeFolder(UUID.randomUUID());
    Mockito.verify(folderRepository).save(captorFolderEntity.capture());

    FolderEntity folderEntityVal = captorFolderEntity.getValue();
    Assertions.assertNotNull(folderEntityVal.getDeletedOn());
  }

  @Test
  void when_getFolderDetails_ShouldThrowFolderNotFound() {

    Assertions.assertThrows(
        FolderNotFoundException.class, () -> folderService.getFolderDetails(UUID.randomUUID()));
  }

  @Test
  void getFolderDetails() {
    FolderEntity expectedFolderEntity = FolderEntityUtil.getFolderWithParent();
    when(folderRepository.findById(any())).thenReturn(Optional.of(expectedFolderEntity));

    FolderResponse actualFolderResponse =
        folderService.getFolderDetails(expectedFolderEntity.getId());

    this.assertFolderEntityAndFolderResponse(expectedFolderEntity, actualFolderResponse);
    verify(folderRepository, times(1)).findById(any());
  }

  private void assertFolderEntityAndFolderResponse(
      FolderEntity expectedFolderEntity, FolderResponse actualFolderResponse) {
    Assertions.assertEquals(
        expectedFolderEntity.getCreatedBy().getId(), actualFolderResponse.getCreatedById());
    Assertions.assertEquals(expectedFolderEntity.getName(), actualFolderResponse.getName());
    Assertions.assertEquals(
        expectedFolderEntity.getBucket().getId(), actualFolderResponse.getBucketId());
  }
}
