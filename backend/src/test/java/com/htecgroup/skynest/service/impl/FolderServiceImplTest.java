package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.folder.FolderAlreadyDeletedException;
import com.htecgroup.skynest.exception.folder.FolderNotFoundException;
import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.FolderEntity;
import com.htecgroup.skynest.model.request.FolderCreateRequest;
import com.htecgroup.skynest.model.request.FolderEditRequest;
import com.htecgroup.skynest.model.request.MoveFolderToBucketRequest;
import com.htecgroup.skynest.model.request.MoveFolderToFolderRequest;
import com.htecgroup.skynest.model.response.FolderResponse;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.repository.FolderRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.ActionService;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.utils.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FolderServiceImplTest {

  @Spy @InjectMocks FolderServiceImpl folderService;
  @Mock private FolderRepository folderRepository;
  @Mock private BucketRepository bucketRepository;
  @Mock private CurrentUserService currentUserService;

  @Mock private ActionService actionService;
  @Mock private UserRepository userRepository;
  @Spy private ModelMapper modelMapper;
  @Captor private ArgumentCaptor<FolderEntity> captorFolderEntity;

  @Test
  void when_AlreadyDeletedFolder_deletedFolder_ShouldThrowFolderAlreadyDeleted() {
    FolderEntity folderEntity = FolderEntityUtil.getDeletedFolder();
    when(folderRepository.findById(any())).thenReturn(Optional.of(folderEntity));
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
    when(folderRepository.findById(any())).thenReturn(Optional.of(folderEntity));

    folderService.removeFolder(UUID.randomUUID());
    Mockito.verify(folderRepository).save(captorFolderEntity.capture());

    FolderEntity folderEntityVal = captorFolderEntity.getValue();
    Assertions.assertNotNull(folderEntityVal.getDeletedOn());
  }

  @Test
  void createFolder_without_parent() {
    FolderEntity exceptedFolderEntity = FolderEntityUtil.getFolderWithoutParent();
    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedWorkerUser());
    when(userRepository.getById(any())).thenReturn(UserEntityUtil.getVerified());
    when(bucketRepository.findById(any()))
        .thenReturn(Optional.of(BucketEntityUtil.getPrivateBucket()));
    doReturn(FolderEntityUtil.getFolderWithoutParent())
        .when(folderRepository)
        .findFolderById(any());
    when(folderRepository.save(any())).thenReturn(exceptedFolderEntity);

    FolderCreateRequest folderCreateRequest = FolderCreateRequestUtil.get();
    FolderResponse actualFolderResponse = folderService.createFolder(folderCreateRequest);
    this.assertFolderEntityAndFolderResponse(exceptedFolderEntity, actualFolderResponse);
  }

  @Test
  void createFolder_with_parent() {
    FolderEntity exceptedFolderEntity = FolderEntityUtil.getFolderWithParent();
    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedWorkerUser());
    when(userRepository.getById(any())).thenReturn(UserEntityUtil.getVerified());
    when(bucketRepository.findById(any()))
        .thenReturn(Optional.of(BucketEntityUtil.getPrivateBucket()));
    doReturn(FolderEntityUtil.getFolderWithParent()).when(folderRepository).findFolderById(any());
    when(folderRepository.save(any())).thenReturn(exceptedFolderEntity);

    FolderCreateRequest folderCreateRequest = FolderCreateRequestUtil.get();
    FolderResponse actualFolderResponse = folderService.createFolder(folderCreateRequest);
    this.assertFolderEntityAndFolderResponse(exceptedFolderEntity, actualFolderResponse);
    Assertions.assertEquals(
        exceptedFolderEntity.getParentFolder().getId(), actualFolderResponse.getParentFolderId());
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

  @Test
  void editFolder() {
    FolderEntity expectedFolderEntity = FolderEntityUtil.getFolderWithoutParent();
    when(folderRepository.findById(any())).thenReturn(Optional.of(expectedFolderEntity));
    when(folderRepository.save(any())).thenReturn(expectedFolderEntity);

    FolderEditRequest folderEditRequest = FolderEditRequestUtil.get();
    FolderResponse actualFolderResponse =
        folderService.editFolder(folderEditRequest, expectedFolderEntity.getId());

    this.assertFolderEntityAndFolderResponse(expectedFolderEntity, actualFolderResponse);
    verify(folderRepository, times(1)).findById(expectedFolderEntity.getId());
    verify(folderRepository, times(1)).save(expectedFolderEntity);
    verifyNoMoreInteractions(folderRepository);
  }

  @Test
  void when_edit_deletedFolder_ShouldThrowFolderAlreadyDeleted() {
    FolderEntity folderEntity = FolderEntityUtil.getDeletedFolder();
    FolderEditRequest folderEditRequest = FolderEditRequestUtil.get();
    when(folderRepository.findById(any())).thenReturn(Optional.of(folderEntity));
    String expectedErrorMessage = FolderAlreadyDeletedException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            FolderAlreadyDeletedException.class,
            () -> folderService.editFolder(folderEditRequest, UUID.randomUUID()));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
    verify(folderRepository, times(1)).findById(any());
  }

  @Test
  void getAllRootFolders() {
    List<FolderEntity> expectedFolders =
        new ArrayList<>(Collections.singleton(FolderEntityUtil.getFolderWithoutParent()));
    when(folderRepository.findAllByBucketIdAndParentFolderIsNull(any()))
        .thenReturn(expectedFolders);

    List<FolderResponse> actualFolders =
        folderService.getAllRootFolders(
            FolderEntityUtil.getFolderWithoutParent().getBucket().getId());

    Assertions.assertEquals(expectedFolders.size(), actualFolders.size());
    this.assertFolderEntityAndFolderResponse(expectedFolders.get(0), actualFolders.get(0));
    verify(folderRepository, times(1)).findAllByBucketIdAndParentFolderIsNull(any());
  }

  @Test
  void moveFolderToBucket() {
    FolderEntity expectedFolderEntity = FolderEntityUtil.getFolderWithoutParent();
    when(folderRepository.findById(any())).thenReturn(Optional.of(expectedFolderEntity));
    BucketEntity bucketEntity = BucketEntityUtil.getPrivateBucket();
    when(bucketRepository.findById(any())).thenReturn(Optional.of(bucketEntity));

    MoveFolderToBucketRequest moveFolderToBucketRequest = MoveFolderRequestUtil.getToBucket();
    folderService.moveFolderToBucket(moveFolderToBucketRequest, UUID.randomUUID());

    Mockito.verify(folderRepository).save(captorFolderEntity.capture());
    Assertions.assertEquals(captorFolderEntity.getValue().getBucket(), bucketEntity);
    verify(folderRepository, times(1)).findById(any());
    verify(bucketRepository, times(1)).findById(any());
  }

  @Test
  void moveFolderToFolder() {
    UUID uuid = UUID.randomUUID();
    MoveFolderToFolderRequest moveFolderToFolderRequest = MoveFolderRequestUtil.getToFolder();
    FolderEntity expectedFolderEntity = FolderEntityUtil.getFolderWithParent();
    when(folderRepository.findById(uuid)).thenReturn(Optional.of(expectedFolderEntity));
    FolderEntity parentFolderEntity = FolderEntityUtil.getFolderWithoutParent();
    when(folderRepository.findById(moveFolderToFolderRequest.getDestinationParentFolderId()))
        .thenReturn(Optional.of(parentFolderEntity));

    folderService.moveFolderToFolder(moveFolderToFolderRequest, uuid);

    Mockito.verify(folderRepository).save(captorFolderEntity.capture());
    Assertions.assertEquals(captorFolderEntity.getValue().getParentFolder(), parentFolderEntity);
    verify(folderRepository, times(2)).findById(any());
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
