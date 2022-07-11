package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.annotation.ParentFolderIsInTheSameBucket;
import com.htecgroup.skynest.exception.buckets.BucketNotFoundException;
import com.htecgroup.skynest.exception.folder.FolderAlreadyDeletedException;
import com.htecgroup.skynest.exception.folder.FolderAlreadyInsideBucketException;
import com.htecgroup.skynest.exception.folder.FolderAlreadyInsideFolderException;
import com.htecgroup.skynest.exception.folder.FolderNotFoundException;
import com.htecgroup.skynest.model.dto.FolderDto;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.entity.ActionType;
import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.FolderEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.model.request.FolderCreateRequest;
import com.htecgroup.skynest.model.request.MoveFolderToBucketRequest;
import com.htecgroup.skynest.model.request.MoveFolderToFolderRequest;
import com.htecgroup.skynest.model.response.FolderResponse;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.repository.FolderRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.ActionService;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.service.FolderService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Validated
public class FolderServiceImpl implements FolderService {

  private ModelMapper modelMapper;
  private FolderRepository folderRepository;
  private CurrentUserService currentUserService;

  private UserRepository userRepository;

  private BucketRepository bucketRepository;

  private ActionService actionService;

  @Override
  public FolderResponse createFolder(
      @Valid @ParentFolderIsInTheSameBucket FolderCreateRequest folderCreateRequest) {
    FolderEntity folderEntity = modelMapper.map(folderCreateRequest, FolderEntity.class);

    LoggedUserDto loggedUserDto = currentUserService.getLoggedUser();
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
        bucketRepository.findById(bucketEntityId).orElseThrow(BucketNotFoundException::new);
    bucketEntity = modelMapper.map(bucketEntity, BucketEntity.class);
    FolderEntity parentFolderEntity = folderRepository.findFolderById(parentFolderEntityId);

    folderEntity.setParentFolder(parentFolderEntity);
    folderEntity.setBucket(bucketEntity);
    folderEntity.setCreatedBy(currentUser);

    return folderEntity;
  }

  @Override
  public void removeFolder(UUID uuid) {
    FolderDto folderDto =
        modelMapper.map(
            folderRepository.findById(uuid).orElseThrow(FolderNotFoundException::new),
            FolderDto.class);
    if (folderDto.isDeleted()) {
      throw new FolderAlreadyDeletedException();
    }
    FolderDto deletedFolderDto = folderDto.deleteFolder();
    FolderEntity folderEntity =
        folderRepository.save(modelMapper.map(deletedFolderDto, FolderEntity.class));
    actionService.recordAction(Collections.singleton(folderEntity), ActionType.DELETE);
  }

  @Override
  public FolderResponse getFolderDetails(UUID uuid) {
    FolderEntity folderEntity =
        folderRepository.findById(uuid).orElseThrow(FolderNotFoundException::new);
    FolderResponse folderResponse = modelMapper.map(folderEntity, FolderResponse.class);
    return folderResponse;
  }

  @Override
  public List<FolderResponse> getAllRootFolders(UUID bucketId) {
    List<FolderEntity> allFolders =
        folderRepository.findAllByBucketIdAndParentFolderIsNull(bucketId);
    return asFolderResponseList(allFolders);
  }

  @Override
  public List<FolderResponse> getAllFoldersWithParent(UUID parentFolderId) {
    List<FolderEntity> allFolders = folderRepository.findAllByParentFolderId(parentFolderId);
    return asFolderResponseList(allFolders);
  }

  @Override
  public void moveFolderToBucket(MoveFolderToBucketRequest moveFolderToBucketRequest, UUID uuid) {
    FolderEntity folderEntity =
        folderRepository.findById(uuid).orElseThrow(FolderNotFoundException::new);
    BucketEntity bucketEntity =
        bucketRepository
            .findById(moveFolderToBucketRequest.getDestinationBucketId())
            .orElseThrow(BucketNotFoundException::new);
    if (folderEntity.getBucket().getId() == bucketEntity.getId()) {
      throw new FolderAlreadyInsideBucketException();
    }
    folderEntity.setBucket(bucketEntity);
    folderRepository.save(folderEntity);
    actionService.recordAction(Collections.singleton(folderEntity), ActionType.MOVE);
  }

  @Override
  public void moveFolderToFolder(MoveFolderToFolderRequest moveFolderRequest, UUID uuid) {
    FolderEntity folderEntity =
        folderRepository.findById(uuid).orElseThrow(FolderNotFoundException::new);
    FolderEntity parentFolderEntity =
        folderRepository
            .findById(moveFolderRequest.getDestinationParentFolderId())
            .orElseThrow(FolderNotFoundException::new);
    if (folderEntity.getParentFolder().getId() == parentFolderEntity.getId()) {
      throw new FolderAlreadyInsideFolderException();
    }
    folderEntity.setParentFolder(parentFolderEntity);
    folderRepository.save(folderEntity);
    actionService.recordAction(Collections.singleton(folderEntity), ActionType.MOVE);
  }

  private List<FolderResponse> asFolderResponseList(List<FolderEntity> allFolders) {
    return allFolders.stream()
        .map(folder -> modelMapper.map(folder, FolderResponse.class))
        .collect(Collectors.toList());
  }
}
