package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.folder.FolderNotFoundException;
import com.htecgroup.skynest.model.entity.FolderEntity;
import com.htecgroup.skynest.model.request.FolderCreateRequest;
import com.htecgroup.skynest.model.response.FolderResponse;
import com.htecgroup.skynest.repository.FolderRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.BucketService;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.utils.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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

  @Test
  void createFolder_without_parent() {
    FolderEntity exceptedFolderEntity = FolderEntityUtil.getFolderWithoutParent();
    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedWorkerUser());
    when(userRepository.getById(any())).thenReturn(UserEntityUtil.getVerified());
    when(bucketService.findBucketById(any())).thenReturn(BucketDtoUtil.getNotDeletedBucket());
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
    when(bucketService.findBucketById(any())).thenReturn(BucketDtoUtil.getNotDeletedBucket());
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

  private void assertFolderEntityAndFolderResponse(
      FolderEntity expectedFolderEntity, FolderResponse actualFolderResponse) {
    Assertions.assertEquals(
        expectedFolderEntity.getCreatedBy().getId(), actualFolderResponse.getCreatedById());
    Assertions.assertEquals(expectedFolderEntity.getName(), actualFolderResponse.getName());
    Assertions.assertEquals(
        expectedFolderEntity.getBucket().getId(), actualFolderResponse.getBucketId());
  }
}
