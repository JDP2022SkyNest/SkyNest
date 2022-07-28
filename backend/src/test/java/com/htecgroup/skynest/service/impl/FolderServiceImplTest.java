package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.folder.*;
import com.htecgroup.skynest.model.entity.FolderEntity;
import com.htecgroup.skynest.model.request.FolderCreateRequest;
import com.htecgroup.skynest.model.request.FolderEditRequest;
import com.htecgroup.skynest.model.response.FileResponse;
import com.htecgroup.skynest.model.response.FolderResponse;
import com.htecgroup.skynest.model.response.ShortFolderResponse;
import com.htecgroup.skynest.model.response.StorageContentResponse;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.repository.FolderRepository;
import com.htecgroup.skynest.repository.ObjectRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.*;
import com.htecgroup.skynest.utils.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FolderServiceImplTest {

  @Spy @InjectMocks FolderServiceImpl folderService;
  @Mock private FolderRepository folderRepository;
  @Mock private BucketRepository bucketRepository;
  @Mock private ObjectRepository objectRepository;
  @Mock private CurrentUserService currentUserService;

  @Mock private PermissionService permissionService;

  @Mock private ActionService actionService;
  @Mock private UserRepository userRepository;
  @Spy private ModelMapper modelMapper;
  @Mock private FileService fileService;
  @Mock private TagService tagService;

  @Mock private FolderValidatorService folderValidatorService;
  @Captor private ArgumentCaptor<FolderEntity> captorFolderEntity;

  @Test
  void when_AlreadyDeletedFolder_deletedFolder_ShouldThrowFolderAlreadyDeleted() {
    FolderEntity folderEntity = FolderEntityUtil.getDeletedFolder();
    when(folderRepository.findById(any())).thenReturn(Optional.of(folderEntity));
    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedWorkerUser());
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
    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedWorkerUser());

    folderService.removeFolder(UUID.randomUUID());
    Mockito.verify(folderRepository).save(captorFolderEntity.capture());

    FolderEntity folderEntityVal = captorFolderEntity.getValue();
    Assertions.assertTrue(folderEntityVal.isDeleted());
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
    when(folderRepository.findAllByBucketIdAndParentFolderIsNullOrderByNameAscCreatedOnDesc(any()))
        .thenReturn(expectedFolders);

    List<FolderResponse> actualFolders =
        folderService.getAllRootFolders(
            FolderEntityUtil.getFolderWithoutParent().getBucket().getId());

    Assertions.assertEquals(expectedFolders.size(), actualFolders.size());
    this.assertFolderEntityAndFolderResponse(expectedFolders.get(0), actualFolders.get(0));
    verify(folderRepository, times(1))
        .findAllByBucketIdAndParentFolderIsNullOrderByNameAscCreatedOnDesc(any());
  }

  @Test
  void when_folderAlreadyInsideFolder_shouldThrowFolderInsideFolderException() {
    FolderEntity folderEntity = FolderEntityUtil.getFolderWithParent();
    UUID uuid = FolderEntityUtil.getFolderWithParent().getId();
    when(folderRepository.findById(uuid)).thenReturn(Optional.of(folderEntity));
    FolderEntity parentFolder = FolderEntityUtil.getFolderWithoutParent();
    UUID destinationUuid = FolderEntityUtil.getFolderWithoutParent().getId();
    when(folderRepository.findById(destinationUuid)).thenReturn(Optional.of(parentFolder));
    doThrow(new FolderAlreadyInsideFolderException())
        .when(folderValidatorService)
        .checkIfFolderAlreadyInsideFolder(folderEntity, parentFolder);

    String expectedErrorMessage = FolderAlreadyInsideFolderException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            FolderAlreadyInsideFolderException.class,
            () -> folderService.moveFolderToFolder(uuid, destinationUuid));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void when_folderAlreadyInsideRoot_shouldThrowFolderAlreadyInsideRootException() {
    FolderEntity folderEntity = FolderEntityUtil.getFolderWithoutParent();
    when(folderRepository.findById(any())).thenReturn(Optional.of(folderEntity));
    doThrow(new FolderAlreadyInsideBucketException())
        .when(folderValidatorService)
        .checkIfFolderAlreadyInsideRoot(folderEntity);

    String expectedErrorMessage = FolderAlreadyInsideBucketException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            FolderAlreadyInsideBucketException.class,
            () -> folderService.moveFolderToRoot(UUID.randomUUID()));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void
      when_destinationFolderIsChildFolder_shouldThrowFolderCanNotBeMovedInsideChildFolderException() {
    FolderEntity folderEntity = FolderEntityUtil.getFolderWithParent();
    UUID uuid = FolderEntityUtil.getFolderWithParent().getId();
    when(folderRepository.findById(uuid)).thenReturn(Optional.of(folderEntity));
    FolderEntity parentFolder = FolderEntityUtil.getFolderWithoutParent();
    UUID destinationUuid = FolderEntityUtil.getFolderWithoutParent().getId();
    when(folderRepository.findById(destinationUuid)).thenReturn(Optional.of(parentFolder));
    doThrow(new FolderCanNotBeMovedInsideChildFolderException())
        .when(folderValidatorService)
        .checkIfDestinationFolderIsChildFolder(parentFolder, folderEntity);

    String expectedErrorMessage = FolderCanNotBeMovedInsideChildFolderException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            FolderCanNotBeMovedInsideChildFolderException.class,
            () -> folderService.moveFolderToFolder(destinationUuid, uuid));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void when_moveFolderToBucketRoot_shouldMoveFolderToRoot() {
    FolderEntity expectedFolderEntity = FolderEntityUtil.getFolderWithParent();
    when(folderRepository.findById(any())).thenReturn(Optional.of(expectedFolderEntity));
    when(folderRepository.save(any())).thenReturn(expectedFolderEntity);
    UUID folderUuid = FolderEntityUtil.getFolderWithParent().getId();
    folderService.moveFolderToRoot(folderUuid);
    Assertions.assertNull(expectedFolderEntity.getParentFolder());
    verify(folderRepository, times(1)).findById(folderUuid);
    verify(folderRepository, times(1)).save(expectedFolderEntity);
  }

  @Test
  void when_moveFolderToFolder_shouldMoveFolderToFolder() {
    UUID uuid = UUID.randomUUID();
    UUID destUUID = UUID.randomUUID();
    FolderEntity expectedFolderEntity = FolderEntityUtil.getFolderWithParent();
    when(folderRepository.findById(uuid)).thenReturn(Optional.of(expectedFolderEntity));
    FolderEntity parentFolderEntity = FolderEntityUtil.getFolderWithParent();
    when(folderRepository.findById(destUUID)).thenReturn(Optional.of(parentFolderEntity));
    when(folderRepository.save(any())).thenReturn(expectedFolderEntity);

    folderService.moveFolderToFolder(uuid, destUUID);

    Assertions.assertNotNull(expectedFolderEntity.getParentFolder());
    verify(folderRepository, times(1)).findById(uuid);
    verify(folderRepository, times(1)).findById(destUUID);
    verify(folderRepository, times(1)).save(expectedFolderEntity);
  }

  @Test
  void getAllFoldersWithParent() {
    FolderEntity folderEntity = FolderEntityUtil.getFolderWithParent();
    List<FolderEntity> expectedFolders = new ArrayList<>(Collections.singleton(folderEntity));
    when(folderRepository.findAllByParentFolderIdOrderByNameAscCreatedOnDesc(any()))
        .thenReturn(expectedFolders);

    List<FolderResponse> actualFolders =
        folderService.getAllFoldersWithParent(folderEntity.getParentFolder().getId());

    Assertions.assertEquals(expectedFolders.size(), actualFolders.size());
    this.assertFolderEntityAndFolderResponse(expectedFolders.get(0), actualFolders.get(0));
    verify(folderRepository, times(1))
        .findAllByParentFolderIdOrderByNameAscCreatedOnDesc(folderEntity.getParentFolder().getId());
  }

  @Test
  void getFolderContent() {

    FolderEntity folderEntity = FolderEntityUtil.getFolderWithParentAndTags();
    when(folderRepository.findById(any())).thenReturn(Optional.of(folderEntity));
    List<FolderEntity> expectedFolderEntities =
        new ArrayList<>(Collections.singleton(folderEntity));
    when(folderRepository.findAllByParentFolderIdOrderByNameAscCreatedOnDesc(any()))
        .thenReturn(expectedFolderEntities);

    List<FileResponse> expectedFileResponseList =
        new ArrayList<>(Collections.singleton(FileResponseUtil.getFileWithParent()));
    when(fileService.getAllFilesWithParent(any())).thenReturn(expectedFileResponseList);

    List<FolderResponse> expectedFolderResponseList =
        new ArrayList<>(Collections.singleton(FolderResponseUtil.getFolderWithParent()));
    UUID bucketId = folderEntity.getBucket().getId();
    List<ShortFolderResponse> expectedPath =
        Collections.singleton(folderEntity.getParentFolder()).stream()
            .map(folder -> modelMapper.map(folder, ShortFolderResponse.class))
            .collect(Collectors.toList());
    StorageContentResponse expectedStorageContentResponse =
        new StorageContentResponse(
            bucketId, expectedFolderResponseList, expectedFileResponseList, expectedPath);

    when(objectRepository.findById(any()))
        .thenReturn(Optional.of(FolderEntityUtil.getFolderWithoutParent()));

    UUID parentId = FolderEntityUtil.getFolderWithParent().getParentFolder().getId();
    StorageContentResponse actualStorageContentResponse = folderService.getFolderContent(parentId);

    Assertions.assertEquals(expectedStorageContentResponse, actualStorageContentResponse);
    verify(folderRepository, times(1)).findById(parentId);
    verify(folderRepository, times(1)).findAllByParentFolderIdOrderByNameAscCreatedOnDesc(parentId);
    verify(fileService, times(1)).getAllFilesWithParent(parentId);
  }

  @Test
  void when_getFolderContent_ShouldThrowFolderNotFound() {
    String expectedErrorMessage = FolderNotFoundException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            FolderNotFoundException.class, () -> folderService.getFolderContent(UUID.randomUUID()));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
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
