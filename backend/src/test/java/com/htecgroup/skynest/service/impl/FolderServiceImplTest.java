package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.model.entity.FolderEntity;
import com.htecgroup.skynest.model.response.FolderResponse;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.CurrentUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class FolderServiceImplTest {

  @Mock private BucketRepository bucketRepository;
  @Mock private CurrentUserService currentUserService;
  @Mock private UserRepository userRepository;
  @Spy private ModelMapper modelMapper;

  @Test
  void createBucket() {}

  private void assertFolderEntityAndFolderResponse(
      FolderEntity expectedFolderEntity, FolderResponse actualFolderResponse) {
    Assertions.assertEquals(
        expectedFolderEntity.getCreatedBy().getId(), actualFolderResponse.getCreatedById());
    Assertions.assertEquals(expectedFolderEntity.getName(), actualFolderResponse.getName());
    Assertions.assertEquals(
        expectedFolderEntity.getBucket().getName(), actualFolderResponse.getBucketName());
    Assertions.assertEquals(
        expectedFolderEntity.getParentFolder().getName(),
        actualFolderResponse.getParentFolderName());
  }
}
