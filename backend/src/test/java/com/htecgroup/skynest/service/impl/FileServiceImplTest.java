package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.file.FileAlreadyDeletedException;
import com.htecgroup.skynest.exception.file.FileNotFoundException;
import com.htecgroup.skynest.model.entity.FileMetadataEntity;
import com.htecgroup.skynest.model.response.FileResponse;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.repository.FileMetadataRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.ActionService;
import com.htecgroup.skynest.utils.FileMetadataEntityUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {

  @Mock private GridFsOperations operations;
  @Mock private CurrentUserServiceImpl currentUserService;
  @Mock private BucketRepository bucketRepository;
  @Mock private UserRepository userRepository;
  @Mock private FileMetadataRepository fileMetadataRepository;
  @Mock private ActionService actionService;

  @Spy private ModelMapper modelMapper;

  @InjectMocks private FileServiceImpl fileService;

  @Test
  void getFileMetadata_ThrowsFileNotFound() {
    FileMetadataEntity fileMetadata = FileMetadataEntityUtil.get();
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
    FileMetadataEntity fileMetadata = FileMetadataEntityUtil.get();
    when(fileMetadataRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
    Assertions.assertThrows(
        FileNotFoundException.class,
        () -> {
          fileService.downloadFile(fileMetadata.getId());
        });
    verify(fileMetadataRepository, times(1)).findById(any());
  }

  @Test
  void getAllRootFiles() {
    List<FileMetadataEntity> expectedFiles =
        new ArrayList<>(Collections.singleton(FileMetadataEntityUtil.get()));
    when(fileMetadataRepository.findAllByBucketIdAndParentFolderIsNull(any()))
        .thenReturn(expectedFiles);

    List<FileResponse> actualFiles =
        fileService.getAllRootFiles(FileMetadataEntityUtil.get().getBucket().getId());

    Assertions.assertEquals(expectedFiles.size(), actualFiles.size());
    this.assertFileMetadataEntityAndFileResponse(expectedFiles.get(0), actualFiles.get(0));
    verify(fileMetadataRepository, times(1)).findAllByBucketIdAndParentFolderIsNull(any());
  }

  private void assertFileMetadataEntityAndFileResponse(
      FileMetadataEntity expectedFileMetadataEntity, FileResponse actualFileResponse) {
    Assertions.assertEquals(expectedFileMetadataEntity.getId(), actualFileResponse.getId());
    Assertions.assertEquals(
        expectedFileMetadataEntity.getCreatedBy().getId().toString(),
        actualFileResponse.getCreatedById());
    Assertions.assertEquals(expectedFileMetadataEntity.getName(), actualFileResponse.getName());
    Assertions.assertEquals(
        expectedFileMetadataEntity.getBucket().getId().toString(),
        actualFileResponse.getBucketId());
    Assertions.assertEquals(expectedFileMetadataEntity.getType(), actualFileResponse.getType());
  }

  @Test
  void deleteFile_ThrowsFileNotFound() {
    UUID fileId = FileMetadataEntityUtil.get().getId();

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
