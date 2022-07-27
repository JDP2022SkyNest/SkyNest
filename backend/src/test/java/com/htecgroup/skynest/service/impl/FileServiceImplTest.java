package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.file.FileAlreadyDeletedException;
import com.htecgroup.skynest.exception.file.FileAlreadyInsideFolderException;
import com.htecgroup.skynest.exception.file.FileAlreadyInsideRootException;
import com.htecgroup.skynest.exception.file.FileNotFoundException;
import com.htecgroup.skynest.model.entity.FileMetadataEntity;
import com.htecgroup.skynest.model.entity.FolderEntity;
import com.htecgroup.skynest.model.request.FileInfoEditRequest;
import com.htecgroup.skynest.model.response.FileResponse;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.repository.FileMetadataRepository;
import com.htecgroup.skynest.repository.FolderRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.ActionService;
import com.htecgroup.skynest.service.FileContentService;
import com.htecgroup.skynest.service.PermissionService;
import com.htecgroup.skynest.service.TagService;
import com.htecgroup.skynest.utils.FileEditRequestUtil;
import com.htecgroup.skynest.utils.FileMetadataEntityUtil;
import com.htecgroup.skynest.utils.FolderEntityUtil;
import com.htecgroup.skynest.utils.LoggedUserDtoUtil;
import com.htecgroup.skynest.utils.tag.TagResponseUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {

  @Mock private FileContentService fileContentService;
  @Mock private CurrentUserServiceImpl currentUserService;
  @Mock private BucketRepository bucketRepository;
  @Mock private UserRepository userRepository;
  @Mock private PermissionService permissionService;
  @Mock private FolderRepository folderRepository;
  @Mock private FileMetadataRepository fileMetadataRepository;
  @Mock private ActionService actionService;
  @Mock private TagService tagService;

  @Spy private ModelMapper modelMapper;

  @InjectMocks private FileServiceImpl fileService;

  @Test
  void getFileMetadata_ThrowsFileNotFound() {
    FileMetadataEntity fileMetadata = FileMetadataEntityUtil.getRootFileMetadataEntity();
    when(fileMetadataRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
    Assertions.assertThrows(
        FileNotFoundException.class,
        () -> {
          fileService.getFileMetadata(fileMetadata.getId());
        });
    verify(fileMetadataRepository, times(1)).findById(any());
  }

  @Test
  void downloadFile_ThrowsFileNotFound() {
    FileMetadataEntity fileMetadata = FileMetadataEntityUtil.getRootFileMetadataEntity();
    when(fileMetadataRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
    Assertions.assertThrows(
        FileNotFoundException.class,
        () -> {
          fileService.downloadFile(fileMetadata.getId());
        });
    verify(fileMetadataRepository, times(1)).findById(any());
  }

  @Test
  void editFileInfo() {
    FileMetadataEntity expectedFileEntity = FileMetadataEntityUtil.getRootFileMetadataEntity();
    when(fileMetadataRepository.findById(any())).thenReturn(Optional.of(expectedFileEntity));
    when(fileMetadataRepository.save(any())).thenReturn(expectedFileEntity);
    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedWorkerUser());

    FileInfoEditRequest fileInfoEditRequest = FileEditRequestUtil.get();
    FileResponse actualFileResponse =
        fileService.editFileInfo(fileInfoEditRequest, expectedFileEntity.getId());

    this.assertFileMetadataEntityAndFileResponse(expectedFileEntity, actualFileResponse);
    verify(fileMetadataRepository, times(1)).findById(expectedFileEntity.getId());
    verify(fileMetadataRepository, times(1)).save(expectedFileEntity);
    verifyNoMoreInteractions(fileMetadataRepository);
  }

  @Test
  void getAllRootFiles() {
    FileMetadataEntity fileMetadataEntity = FileMetadataEntityUtil.getRootFileMetadataEntity();
    List<FileMetadataEntity> expectedFiles =
        new ArrayList<>(Collections.singleton(fileMetadataEntity));
    when(fileMetadataRepository.findAllByBucketIdAndParentFolderIsNullOrderByNameAscCreatedOnDesc(
            any()))
        .thenReturn(expectedFiles);
    when(tagService.getTagsForObject(any()))
        .thenReturn(Collections.singletonList(TagResponseUtil.get()));

    List<FileResponse> actualFiles =
        fileService.getAllRootFiles(fileMetadataEntity.getBucket().getId());

    Assertions.assertEquals(expectedFiles.size(), actualFiles.size());
    this.assertFileMetadataEntityAndFileResponse(expectedFiles.get(0), actualFiles.get(0));
    verify(fileMetadataRepository, times(1))
        .findAllByBucketIdAndParentFolderIsNullOrderByNameAscCreatedOnDesc(
            fileMetadataEntity.getBucket().getId());
    verify(tagService, times(1)).getTagsForObject(expectedFiles.get(0).getId());
  }

  @Test
  void getAllFilesWithParent() {
    FileMetadataEntity fileMetadataEntity = FileMetadataEntityUtil.getNotRootFileMetadataEntity();
    List<FileMetadataEntity> expectedFiles =
        new ArrayList<>(Collections.singleton(fileMetadataEntity));
    when(fileMetadataRepository.findAllByParentFolderIdOrderByNameAscCreatedOnDesc(any()))
        .thenReturn(expectedFiles);
    when(tagService.getTagsForObject(any()))
        .thenReturn(Collections.singletonList(TagResponseUtil.get()));

    List<FileResponse> actualFiles =
        fileService.getAllFilesWithParent(fileMetadataEntity.getParentFolder().getId());

    Assertions.assertEquals(expectedFiles.size(), actualFiles.size());
    this.assertFileMetadataEntityAndFileResponse(expectedFiles.get(0), actualFiles.get(0));
    verify(fileMetadataRepository, times(1))
        .findAllByParentFolderIdOrderByNameAscCreatedOnDesc(
            fileMetadataEntity.getParentFolder().getId());
    verify(tagService, times(1)).getTagsForObject(expectedFiles.get(0).getId());
  }

  @Test
  void when_fileAlreadyInsideFolder_shouldThrowFileAlreadyInsideFolderException() {
    FileMetadataEntity fileMetadataEntity = FileMetadataEntityUtil.getNotRootFileMetadataEntity();
    when(fileMetadataRepository.findById(any())).thenReturn(Optional.of(fileMetadataEntity));
    FolderEntity folderEntity = fileMetadataEntity.getParentFolder();
    when(folderRepository.findById(any())).thenReturn(Optional.of(folderEntity));

    String expectedErrorMessage = FileAlreadyInsideFolderException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            FileAlreadyInsideFolderException.class,
            () -> fileService.moveFileToFolder(UUID.randomUUID(), UUID.randomUUID()));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void when_moveFileToFolder_shouldMoveToFolder() {
    FileMetadataEntity expectedFileMetadataEntity =
        FileMetadataEntityUtil.getNotRootFileMetadataEntity();
    when(fileMetadataRepository.findById(any()))
        .thenReturn(Optional.of(expectedFileMetadataEntity));
    FolderEntity folderEntity = FolderEntityUtil.getFolderWithoutParent();
    when(folderRepository.findById(any())).thenReturn(Optional.of(folderEntity));
    when(fileMetadataRepository.save(any())).thenReturn(expectedFileMetadataEntity);

    UUID fileUuid = FileMetadataEntityUtil.getNotRootFileMetadataEntity().getId();
    UUID folderUuid = FolderEntityUtil.getFolderWithoutParent().getId();
    fileService.moveFileToFolder(fileUuid, folderUuid);

    Assertions.assertNotNull(expectedFileMetadataEntity.getParentFolder());
    verify(fileMetadataRepository, times(1)).findById(fileUuid);
    verify(folderRepository, times(1)).findById(folderUuid);
  }

  @Test
  void when_fileAlreadyInsideRoot_shouldThrowFileAlreadyInsideRootException() {
    FileMetadataEntity fileMetadataEntity = FileMetadataEntityUtil.getRootFileMetadataEntity();
    when(fileMetadataRepository.findById(any())).thenReturn(Optional.of(fileMetadataEntity));

    String expectedErrorMessage = FileAlreadyInsideRootException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            FileAlreadyInsideRootException.class,
            () -> fileService.moveFileToRoot(UUID.randomUUID()));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void moveFileToRoot() {
    FileMetadataEntity expectedFileMetadataEntity =
        FileMetadataEntityUtil.getNotRootFileMetadataEntity();
    when(fileMetadataRepository.findById(any()))
        .thenReturn(Optional.of(expectedFileMetadataEntity));
    when(fileMetadataRepository.save(any())).thenReturn(expectedFileMetadataEntity);

    UUID fileUuid = FileMetadataEntityUtil.getNotRootFileMetadataEntity().getId();
    fileService.moveFileToRoot(fileUuid);

    Assertions.assertNull(expectedFileMetadataEntity.getParentFolder());
    verify(fileMetadataRepository, times(1)).findById(fileUuid);
    verify(fileMetadataRepository, times(1)).save(expectedFileMetadataEntity);
  }

  private void assertFileMetadataEntityAndFileResponse(
      FileMetadataEntity expectedFileMetadataEntity, FileResponse actualFileResponse) {
    Assertions.assertEquals(expectedFileMetadataEntity.getId(), actualFileResponse.getId());
    Assertions.assertEquals(
        expectedFileMetadataEntity.getCreatedBy().getId(), actualFileResponse.getCreatedById());
    Assertions.assertEquals(expectedFileMetadataEntity.getName(), actualFileResponse.getName());
    Assertions.assertEquals(
        expectedFileMetadataEntity.getBucket().getId(), actualFileResponse.getBucketId());
    Assertions.assertEquals(expectedFileMetadataEntity.getType(), actualFileResponse.getType());
    if (expectedFileMetadataEntity.getParentFolder() != null) {
      Assertions.assertEquals(
          expectedFileMetadataEntity.getParentFolder().getId(),
          actualFileResponse.getParentFolderId());
    }
  }

  @Test
  void deleteFile_ThrowsFileNotFound() {
    UUID fileId = FileMetadataEntityUtil.getRootFileMetadataEntity().getId();

    when(fileMetadataRepository.findById(fileId)).thenReturn(Optional.empty());

    String expectedErrorMessage = FileNotFoundException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(FileNotFoundException.class, () -> fileService.deleteFile(fileId));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());

    verify(fileMetadataRepository, times(1)).findById(fileId);
    verify(actionService, times(0)).recordAction(any(), any());
    verify(actionService, times(0)).recordAction(any(), any(), any());
  }

  @Test
  void deleteFile_ThrowsFileAlreadyDeleted() {
    FileMetadataEntity fileMetadataEntity = FileMetadataEntityUtil.getDeleted();
    UUID fileId = fileMetadataEntity.getId();

    when(fileMetadataRepository.findById(fileId)).thenReturn(Optional.of(fileMetadataEntity));

    String expectedErrorMessage = FileAlreadyDeletedException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            FileAlreadyDeletedException.class, () -> fileService.deleteFile(fileId));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());

    verify(fileMetadataRepository, times(1)).findById(fileId);
    verify(actionService, times(0)).recordAction(any(), any());
    verify(actionService, times(0)).recordAction(any(), any(), any());
  }
}
