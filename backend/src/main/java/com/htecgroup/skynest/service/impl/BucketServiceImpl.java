package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.buckets.BucketAccessDeniedException;
import com.htecgroup.skynest.exception.buckets.BucketAlreadyDeletedException;
import com.htecgroup.skynest.exception.buckets.BucketAlreadyRestoredException;
import com.htecgroup.skynest.exception.buckets.BucketNotFoundException;
import com.htecgroup.skynest.lambda.LambdaType;
import com.htecgroup.skynest.model.dto.BucketDto;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.entity.*;
import com.htecgroup.skynest.model.request.BucketCreateRequest;
import com.htecgroup.skynest.model.request.BucketEditRequest;
import com.htecgroup.skynest.model.response.BucketResponse;
import com.htecgroup.skynest.model.response.FileResponse;
import com.htecgroup.skynest.model.response.FolderResponse;
import com.htecgroup.skynest.model.response.StorageContentResponse;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Validated
@AllArgsConstructor
public class BucketServiceImpl implements BucketService {

  private BucketRepository bucketRepository;
  private ModelMapper modelMapper;
  private CurrentUserService currentUserService;
  private UserRepository userRepository;
  private FolderService folderService;
  private FileService fileService;
  private ActionService actionService;
  private PermissionService permissionService;

  @Override
  public BucketResponse createBucket(BucketCreateRequest bucketCreateRequest) {

    BucketEntity bucketEntity = modelMapper.map(bucketCreateRequest, BucketEntity.class);

    LoggedUserDto loggedUserDto = currentUserService.getLoggedUser();
    UserEntity currentUser = userRepository.getById(loggedUserDto.getUuid());
    CompanyEntity currentUserCompany = currentUser.getCompany();

    bucketEntity.setName(bucketEntity.getName().trim());
    bucketEntity.setDescription(bucketEntity.getDescription().trim());

    bucketEntity.setCreatedBy(currentUser);
    bucketEntity.setCompany(currentUserCompany);
    bucketEntity.setIsPublic(false);

    BucketEntity savedBucketEntity = bucketRepository.save(bucketEntity);

    permissionService.grantOwnerForObject(savedBucketEntity);

    actionService.recordAction(Collections.singleton(savedBucketEntity), ActionType.CREATE);

    return modelMapper.map(savedBucketEntity, BucketResponse.class);
  }

  @Override
  public BucketResponse getBucketDetails(UUID uuid) {
    BucketEntity bucketEntity =
        bucketRepository.findById(uuid).orElseThrow(BucketNotFoundException::new);
    if (!bucketEntity.getIsPublic()) {
      permissionService.currentUserHasPermissionForBucket(uuid, AccessType.VIEW);
    }

    actionService.recordAction(Collections.singleton(bucketEntity), ActionType.VIEW);

    return modelMapper.map(bucketEntity, BucketResponse.class);
  }

  @Override
  public BucketDto findBucketByName(String name) {
    BucketEntity bucketEntity =
        bucketRepository.findBucketByName(name).orElseThrow(BucketNotFoundException::new);
    return modelMapper.map(bucketEntity, BucketDto.class);
  }

  @Override
  public void activateLambda(UUID bucketId, LambdaType lambdaType) {
    BucketEntity bucketEntity = findBucketEntityById(bucketId);
    bucketEntity.addLambda(lambdaType);
    actionService.recordAction(Collections.singleton(bucketEntity), ActionType.EDIT);
    bucketRepository.save(bucketEntity);
  }

  @Override
  public List<BucketResponse> getAllBucketsWithTag(UUID tagId, UUID loggedUserId) {
    List<BucketEntity> allBuckets =
        bucketRepository.findAllByTagIdWhereUserCanView(tagId, loggedUserId);
    return asBucketResponseList(allBuckets);
  }

  private List<BucketResponse> asBucketResponseList(List<BucketEntity> allBuckets) {
    return allBuckets.stream()
        .map(bucket -> modelMapper.map(bucket, BucketResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  public void deactivateLambda(UUID bucketId, LambdaType lambda) {
    BucketEntity bucketEntity = findBucketEntityById(bucketId);
    bucketEntity.removeLambda(lambda);
    actionService.recordAction(Collections.singleton(bucketEntity), ActionType.EDIT);
    bucketRepository.save(bucketEntity);
  }

  @Override
  public List<BucketResponse> listAllBuckets() {

    List<BucketEntity> entityList =
        (List<BucketEntity>) bucketRepository.findAllByOrderByNameAscCreatedOnDesc();
    entityList.stream().filter(this::doesCurrentUserHaveViewPermissionOnBucket);

    actionService.recordAction(new HashSet<>(entityList), ActionType.VIEW);

    return entityList.stream()
        .map(e -> modelMapper.map(e, BucketResponse.class))
        .collect(Collectors.toList());
  }

  private boolean doesCurrentUserHaveViewPermissionOnBucket(BucketEntity bucket) {
    try {
      permissionService.currentUserHasPermissionForBucket(bucket.getId(), AccessType.VIEW);
    } catch (BucketAccessDeniedException b) {
      return false;
    }
    return true;
  }

  @Override
  public BucketDto findBucketById(UUID uuid) {
    return modelMapper.map(findBucketEntityById(uuid), BucketDto.class);
  }

  public BucketEntity findBucketEntityById(UUID uuid) {
    return bucketRepository.findById(uuid).orElseThrow(BucketNotFoundException::new);
  }

  @Override
  public void deleteBucket(UUID uuid) {
    BucketDto bucketDto = findBucketById(uuid);
    if (bucketDto.getDeletedOn() != null) {
      throw new BucketAlreadyDeletedException();
    }
    if (!bucketDto.getIsPublic()) {
      permissionService.currentUserHasPermissionForBucket(uuid, AccessType.EDIT);
    }
    BucketDto deletedBucketDto = bucketDto.deleteBucket();
    BucketEntity bucketEntity =
        bucketRepository.save(modelMapper.map(deletedBucketDto, BucketEntity.class));
    actionService.recordAction(Collections.singleton(bucketEntity), ActionType.DELETE);
  }

  @Override
  public void restoreBucket(UUID uuid) {
    BucketDto bucketDto = findBucketById(uuid);
    if (bucketDto.getDeletedOn() == null) {
      throw new BucketAlreadyRestoredException();
    }
    if (!bucketDto.getIsPublic()) {
      permissionService.currentUserHasPermissionForBucket(uuid, AccessType.EDIT);
    }

    BucketDto restoreBucketDto = bucketDto.restoreBucket();
    BucketEntity bucketEntity =
        bucketRepository.save(modelMapper.map(restoreBucketDto, BucketEntity.class));
    actionService.recordAction(Collections.singleton(bucketEntity), ActionType.RESTORE);
  }

  @Override
  public BucketResponse editBucket(BucketEditRequest bucketEditRequest, UUID uuid) {

    BucketEntity bucketEntity = findBucketEntityById(uuid);
    if (bucketEntity.isDeleted()) {
      throw new BucketAlreadyDeletedException();
    }
    if (!bucketEntity.getIsPublic()) {
      permissionService.currentUserHasPermissionForBucket(uuid, AccessType.EDIT);
    }
    bucketEditRequest.setName(bucketEditRequest.getName().trim());
    bucketEditRequest.setDescription(bucketEditRequest.getDescription().trim());

    modelMapper.map(bucketEditRequest, bucketEntity);
    BucketEntity savedBucketEntity = bucketRepository.save(bucketEntity);

    actionService.recordAction(Collections.singleton(savedBucketEntity), ActionType.EDIT);
    return modelMapper.map(savedBucketEntity, BucketResponse.class);
  }

  @Override
  public StorageContentResponse getBucketContent(UUID bucketId) {

    BucketEntity bucketEntity =
        bucketRepository.findById(bucketId).orElseThrow(BucketNotFoundException::new);
    if (!bucketEntity.getIsPublic()) {
      permissionService.currentUserHasPermissionForBucket(bucketId, AccessType.VIEW);
    }
    List<FolderResponse> allFoldersResponse = folderService.getAllRootFolders(bucketId);
    List<FileResponse> allFilesResponse = fileService.getAllRootFiles(bucketId);
    StorageContentResponse storageContentResponse =
        new StorageContentResponse(bucketId, allFoldersResponse, allFilesResponse, null);
    return storageContentResponse;
  }

  @Override
  public List<LambdaType> getActiveLambdas(UUID bucketId) {
    BucketEntity bucketEntity = findBucketEntityById(bucketId);
    return new ArrayList<>(bucketEntity.getLambdaTypes());
  }
}
