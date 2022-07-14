package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.file.FileNotFoundException;
import com.htecgroup.skynest.model.entity.FileMetadataEntity;
import com.htecgroup.skynest.model.entity.FolderEntity;
import com.htecgroup.skynest.model.response.FileResponse;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.repository.FileMetadataRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.ActionService;
import com.htecgroup.skynest.utils.FileMetadataEntityUtil;
import com.htecgroup.skynest.utils.FolderEntityUtil;
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
  void getAllRootFiles() {
    FileMetadataEntity fileMetadataEntity = FileMetadataEntityUtil.getRootFileMetadataEntity();
    List<FileMetadataEntity> expectedFiles =
        new ArrayList<>(Collections.singleton(fileMetadataEntity));
    when(fileMetadataRepository.findAllByBucketIdAndParentFolderIsNull(any()))
        .thenReturn(expectedFiles);

    List<FileResponse> actualFiles =
        fileService.getAllRootFiles(fileMetadataEntity.getBucket().getId());

    Assertions.assertEquals(expectedFiles.size(), actualFiles.size());
    this.assertFileMetadataEntityAndFileResponse(expectedFiles.get(0), actualFiles.get(0));
    verify(fileMetadataRepository, times(1))
        .findAllByBucketIdAndParentFolderIsNull(fileMetadataEntity.getBucket().getId());
  }

  @Test
  void getAllFilesWithParent() {
    FileMetadataEntity fileMetadataEntity = FileMetadataEntityUtil.getNotRootFileMetadataEntity();
    List<FileMetadataEntity> expectedFiles =
        new ArrayList<>(Collections.singleton(fileMetadataEntity));
    when(fileMetadataRepository.findAllByParentFolderId(any())).thenReturn(expectedFiles);

    List<FileResponse> actualFiles =
        fileService.getAllFilesWithParent(fileMetadataEntity.getParentFolder().getId());

    Assertions.assertEquals(expectedFiles.size(), actualFiles.size());
    this.assertFileMetadataEntityAndFileResponse(expectedFiles.get(0), actualFiles.get(0));
    verify(fileMetadataRepository, times(1))
        .findAllByParentFolderId(fileMetadataEntity.getParentFolder().getId());
  }

  @Test
  void moveFileToFolderTest() {
    FileMetadataEntity expectedFileMetadataEntity =
        FileMetadataEntityUtil.getNotRootFileMetadataEntity();
    FolderEntity folderEntity = FolderEntityUtil.getFolderWithoutParent();
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
}
