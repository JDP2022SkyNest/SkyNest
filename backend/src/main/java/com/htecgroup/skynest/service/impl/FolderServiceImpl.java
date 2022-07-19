package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.annotation.ParentFolderIsInTheSameBucket;
import com.htecgroup.skynest.exception.buckets.BucketNotFoundException;
import com.htecgroup.skynest.exception.folder.FolderAlreadyDeletedException;
import com.htecgroup.skynest.exception.folder.FolderNotFoundException;
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
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Validated
public class FolderServiceImpl implements FolderService {

  private ModelMapper modelMapper;
  private FolderRepository folderRepository;
  private CurrentUserService currentUserService;
  private FileService fileService;

  private UserRepository userRepository;

  private BucketRepository bucketRepository;

  private ActionService actionService;
  private PermissionService permissionService;

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
  public FolderResponse editFolder(FolderEditRequest folderEditRequest, UUID folderId) {
    FolderEntity folderEntity =
        folderRepository.findById(folderId).orElseThrow(FolderNotFoundException::new);
    FolderDto folderDto = modelMapper.map(folderEntity, FolderDto.class);

    if (folderDto.isDeleted()) {
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
        folderRepository.findAllByBucketIdAndParentFolderIsNullOrderByNameAscCreatedOn(bucketId);
    return asFolderResponseList(allFolders);
  }

  @Override
  public List<FolderResponse> getAllFoldersWithParent(UUID parentFolderId) {
    List<FolderEntity> allFolders =
        folderRepository.findAllByParentFolderIdOrderByNameAscCreatedOn(parentFolderId);
    return asFolderResponseList(allFolders);
  }

  @Override
  public StorageContentResponse getFolderContent(UUID folderId) {
    FolderEntity parentFolder =
        folderRepository.findById(folderId).orElseThrow(FolderNotFoundException::new);
    UUID bucketId = parentFolder.getBucket().getId();
    List<FolderResponse> allFoldersResponse = getAllFoldersWithParent(folderId);
    List<FileResponse> allFilesResponse = fileService.getAllFilesWithParent(folderId);
    List<ShortFolderResponse> path = asShortFolderResponseList(getPathToFolder(parentFolder));
    StorageContentResponse storageContentResponse =
        new StorageContentResponse(bucketId, allFoldersResponse, allFilesResponse, path);
    return storageContentResponse;
  }

  private List<FolderEntity> getPathToFolder(FolderEntity folderEntity) {

    Deque<FolderEntity> path = new LinkedList<>();
    FolderEntity parentFolder = folderEntity.getParentFolder();
    while (parentFolder != null) {
      path.addFirst(parentFolder);
      parentFolder = parentFolder.getParentFolder();
    }

    return (List<FolderEntity>) path;
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
