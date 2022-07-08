package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.file.FileAlreadyDeletedException;
import com.htecgroup.skynest.exception.file.FileNotFoundException;
import com.htecgroup.skynest.model.entity.FileMetadataEntity;
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

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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
    FileMetadataEntity fileMetadata = FileMetadataEntityUtil.getFileMetadataEntity();
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
    FileMetadataEntity fileMetadata = FileMetadataEntityUtil.getFileMetadataEntity();
    when(fileMetadataRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
    Assertions.assertThrows(
        FileNotFoundException.class,
        () -> {
          fileService.downloadFile(fileMetadata.getId());
        });
    verify(fileMetadataRepository, times(1)).findById(any());
  }

  @Test
  void deleteFile_ThrowsFileNotFound() {
    UUID fileId = FileMetadataEntityUtil.getFileMetadataEntity().getId();

    when(fileMetadataRepository.findById(fileId)).thenReturn(Optional.empty());

    Assertions.assertThrows(FileNotFoundException.class, () -> fileService.deleteFile(fileId));
    verify(fileMetadataRepository, times(1)).findById(any());
  }

  @Test
  void deleteFile_ThrowsFileAlreadyDeleted() {
    FileMetadataEntity fileMetadataEntity = FileMetadataEntityUtil.getFileMetadataEntity();
    fileMetadataEntity.setDeletedOn(LocalDateTime.now());
    UUID fileId = fileMetadataEntity.getId();

    when(fileMetadataRepository.findById(any())).thenReturn(Optional.of(fileMetadataEntity));

    Assertions.assertThrows(
        FileAlreadyDeletedException.class, () -> fileService.deleteFile(fileId));
    verify(fileMetadataRepository, times(1)).findById(any());
  }
}
