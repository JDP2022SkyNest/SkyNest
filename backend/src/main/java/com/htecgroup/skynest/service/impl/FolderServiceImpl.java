package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.annotation.ParentFolderIsInTheSameBucket;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.FolderEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.model.request.FolderCreateRequest;
import com.htecgroup.skynest.model.response.FolderResponse;
import com.htecgroup.skynest.repository.FolderRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.BucketService;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.service.FolderService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.UUID;

@Service
@AllArgsConstructor
@Validated
public class FolderServiceImpl implements FolderService {

  private ModelMapper modelMapper;
  private FolderRepository folderRepository;
  private CurrentUserService currentUserService;

  private UserRepository userRepository;

  private BucketService bucketService;

  @Override
  public FolderResponse createFolder(
      @Valid @ParentFolderIsInTheSameBucket FolderCreateRequest folderCreateRequest) {
    FolderEntity folderEntity = modelMapper.map(folderCreateRequest, FolderEntity.class);

    LoggedUserDto loggedUserDto = currentUserService.getLoggedUser();
    folderEntity =
        setNewFolder(
            folderEntity,
            loggedUserDto.getUuid(),
            folderCreateRequest.getBucketId(),
            folderCreateRequest.getParentFolderId());
    folderEntity = folderRepository.save(folderEntity);
    return modelMapper.map(folderEntity, FolderResponse.class);
  }

  @Override
  public FolderEntity setNewFolder(
      FolderEntity folderEntity,
      UUID currentUserId,
      UUID bucketEntityId,
      UUID parentFolderEntityId) {
    UserEntity currentUser = userRepository.getById(currentUserId);
    BucketEntity bucketEntity =
        modelMapper.map(bucketService.findBucketById(bucketEntityId), BucketEntity.class);
    FolderEntity parentFolderEntity = folderRepository.findFolderById(parentFolderEntityId);

    folderEntity.setParentFolder(parentFolderEntity);
    folderEntity.setBucket(bucketEntity);
    folderEntity.setCreatedBy(currentUser);
    return folderEntity;
  }
}
