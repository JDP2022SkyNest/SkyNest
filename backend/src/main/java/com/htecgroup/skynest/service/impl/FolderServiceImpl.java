package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.annotation.ParentFolderIsInTheSameBucket;
import com.htecgroup.skynest.annotation.actions.RecordAction;
import com.htecgroup.skynest.exception.buckets.BucketNotFoundException;
import com.htecgroup.skynest.exception.folder.FolderAlreadyDeletedException;
import com.htecgroup.skynest.exception.folder.FolderAlreadyRestoredException;
import com.htecgroup.skynest.exception.folder.FolderNotFoundException;
import com.htecgroup.skynest.exception.folder.FolderParentIsDeletedException;
import com.htecgroup.skynest.model.dto.FolderDto;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.entity.ActionType;
import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.FolderEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.model.request.FolderCreateRequest;
import com.htecgroup.skynest.model.request.FolderEditRequest;
import com.htecgroup.skynest.model.response.FileResponse;
import com.htecgroup.skynest.model.response.FolderResponse;
import com.htecgroup.skynest.model.response.ShortFolderResponse;
import com.htecgroup.skynest.model.response.StorageContentResponse;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.repository.FolderRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.*;
import com.htecgroup.skynest.util.FolderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class FolderServiceImpl implements FolderService {

  private ModelMapper modelMapper;
  private FolderRepository folderRepository;
  private CurrentUserService currentUserService;
  private FileService fileService;

  private UserRepository userRepository;

  private BucketRepository bucketRepository;

  private ActionService actionService;
  private PermissionService permissionService;

  private FolderValidatorService folderValidatorService;

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

    permissionService.grantOwnerForObject(folderEntity);

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

    LoggedUserDto currentUser = currentUserService.getLoggedUser();

    FolderDto folderDto =
        modelMapper.map(
            folderRepository.findById(uuid).orElseThrow(FolderNotFoundException::new),
            FolderDto.class);

    log.info(
        "User {} ({}) is attempting to delete folder {} ({})",
        currentUser.getUsername(),
        currentUser.getUuid(),
        folderDto.getName(),
        folderDto.getId());

    if (folderDto.isDeleted()) {
      throw new FolderAlreadyDeletedException();
    }
    FolderDto deletedFolderDto = folderDto.deleteFolder();
    FolderEntity folderEntity =
        folderRepository.save(modelMapper.map(deletedFolderDto, FolderEntity.class));
    actionService.recordAction(Collections.singleton(folderEntity), ActionType.DELETE);
  }

  @Override
  @RecordAction(objectId = "[0].toString()", actionType = ActionType.RESTORE)
  public FolderResponse restoreFolder(UUID folderId) {

    LoggedUserDto currentUser = currentUserService.getLoggedUser();

    FolderEntity folderEntity =
        folderRepository.findById(folderId).orElseThrow(FolderNotFoundException::new);

    log.info(
        "User {} ({}) is attempting to restore folder {} ({})",
        currentUser.getUsername(),
        currentUser.getUuid(),
        folderEntity.getName(),
        folderEntity.getId());

    if (!folderEntity.isDeleted()) throw new FolderAlreadyRestoredException();

    if (folderEntity.getParentFolder() == null
        ? folderEntity.getBucket().isDeleted()
        : folderEntity.getParentFolder().isDeleted()) throw new FolderParentIsDeletedException();

    folderEntity.restore();
    FolderEntity savedFolderEntity = folderRepository.save(folderEntity);

    return modelMapper.map(savedFolderEntity, FolderResponse.class);
  }

  @Override
  public FolderResponse getFolderDetails(UUID uuid) {
    FolderEntity folderEntity =
        folderRepository.findById(uuid).orElseThrow(FolderNotFoundException::new);
    FolderResponse folderResponse = modelMapper.map(folderEntity, FolderResponse.class);
    return folderResponse;
  }

  @Override
  public FolderResponse editFolder(FolderEditRequest folderEditRequest, UUID folderId) {
    FolderEntity folderEntity =
        folderRepository.findById(folderId).orElseThrow(FolderNotFoundException::new);

    if (folderEntity.isDeleted()) {
      throw new FolderAlreadyDeletedException();
    }
    folderEntity.setName(folderEditRequest.getName().trim());

    modelMapper.map(folderEditRequest, folderEntity);
    FolderEntity saveFolderEntity = folderRepository.save(folderEntity);

    actionService.recordAction(Collections.singleton(saveFolderEntity), ActionType.EDIT);
    return modelMapper.map(saveFolderEntity, FolderResponse.class);
  }

  @Override
  public List<FolderResponse> getAllRootFolders(UUID bucketId) {
    List<FolderEntity> allFolders =
        folderRepository.findAllByBucketIdAndParentFolderIsNullOrderByNameAscCreatedOnDesc(
            bucketId);
    return asFolderResponseList(allFolders);
  }

  @Override
  public List<FolderResponse> getAllFoldersWithParent(UUID parentFolderId) {
    List<FolderEntity> allFolders =
        folderRepository.findAllByParentFolderIdOrderByNameAscCreatedOnDesc(parentFolderId);
    return asFolderResponseList(allFolders);
  }

  @Override
  public void moveFolderToRoot(UUID folderId) {
    FolderEntity folderEntity = findFolderEntity(folderId);
    folderValidatorService.checkIfFolderAlreadyInsideRoot(folderEntity);
    folderEntity.moveToRoot(folderEntity);
    saveMoveFolder(folderEntity);
  }

  @Override
  public void moveFolderToFolder(UUID folderId, UUID destinationFolderId) {
    FolderEntity folderEntity = findFolderEntity(folderId);
    FolderEntity parentFolderEntity =
        folderRepository.findById(destinationFolderId).orElseThrow(FolderNotFoundException::new);
    folderValidatorService.checkIfFolderAlreadyInsideFolder(folderEntity, parentFolderEntity);
    folderValidatorService.checkIfDestinationFolderIsChildFolder(folderEntity, parentFolderEntity);
    folderEntity.setParentFolder(parentFolderEntity);
    saveMoveFolder(folderEntity);
  }

  private FolderEntity findFolderEntity(UUID folderID) {
    return folderRepository.findById(folderID).orElseThrow(FolderNotFoundException::new);
  }

  private void saveMoveFolder(FolderEntity folderEntity) {
    folderRepository.save(folderEntity);
    actionService.recordAction(Collections.singleton(folderEntity), ActionType.MOVE);
  }

  @Override
  public StorageContentResponse getFolderContent(UUID folderId) {
    FolderEntity parentFolder =
        folderRepository.findById(folderId).orElseThrow(FolderNotFoundException::new);
    UUID bucketId = parentFolder.getBucket().getId();
    List<FolderResponse> allFoldersResponse = getAllFoldersWithParent(folderId);
    List<FileResponse> allFilesResponse = fileService.getAllFilesWithParent(folderId);
    List<ShortFolderResponse> path =
        asShortFolderResponseList(FolderUtil.getPathToFolder(parentFolder));
    StorageContentResponse storageContentResponse =
        new StorageContentResponse(bucketId, allFoldersResponse, allFilesResponse, path);
    return storageContentResponse;
  }

  private List<FolderResponse> asFolderResponseList(List<FolderEntity> allFolders) {
    return allFolders.stream()
        .map(folder -> modelMapper.map(folder, FolderResponse.class))
        .collect(Collectors.toList());
  }

  private List<ShortFolderResponse> asShortFolderResponseList(List<FolderEntity> folders) {
    return folders.stream()
        .map(folder -> modelMapper.map(folder, ShortFolderResponse.class))
        .collect(Collectors.toList());
  }
}
