package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.annotation.ParentFolderIsInTheSameBucket;
import com.htecgroup.skynest.exception.folder.FolderAlreadyDeletedException;
import com.htecgroup.skynest.exception.folder.FolderNotFoundException;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.entity.ActionType;
import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.FolderEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.model.request.FolderCreateRequest;
import com.htecgroup.skynest.model.request.FolderEditRequest;
import com.htecgroup.skynest.model.response.FolderResponse;
import com.htecgroup.skynest.repository.FolderRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.ActionService;
import com.htecgroup.skynest.service.BucketService;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.service.FolderService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collections;
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

  private ActionService actionService;

  @Override
  public FolderResponse createFolder(
      @Valid @ParentFolderIsInTheSameBucket FolderCreateRequest folderCreateRequest) {
    FolderEntity folderEntity = modelMapper.map(folderCreateRequest, FolderEntity.class);

    LoggedUserDto loggedUserDto = currentUserService.getLoggedUser();
    folderEntity =
        createFolder(
            folderEntity,
            loggedUserDto.getUuid(),
            folderCreateRequest.getBucketId(),
            folderCreateRequest.getParentFolderId());
    folderEntity = folderRepository.save(folderEntity);
    return modelMapper.map(folderEntity, FolderResponse.class);
  }

  private FolderEntity createFolder(
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

  @Override
  public FolderResponse getFolderDetails(UUID uuid) {
    FolderEntity folderEntity =
        folderRepository.findById(uuid).orElseThrow(FolderNotFoundException::new);
    FolderResponse folderResponse = modelMapper.map(folderEntity, FolderResponse.class);
    return folderResponse;
  }

  @Override
  public FolderResponse editFolder(FolderEditRequest folderEditRequest, UUID uuid) {
    FolderEntity folderEntity =
        folderRepository.findById(uuid).orElseThrow(FolderNotFoundException::new);

    if (folderEntity.getDeletedOn() != null) {
      throw new FolderAlreadyDeletedException();
    }
    folderEntity.setName(folderEditRequest.getName().trim());

    modelMapper.map(folderEditRequest, folderEntity);
    FolderEntity saveFolderEntity = folderRepository.save(folderEntity);

    actionService.recordAction(Collections.singleton(saveFolderEntity), ActionType.EDIT);
    return modelMapper.map(saveFolderEntity, FolderResponse.class);
  }
}
