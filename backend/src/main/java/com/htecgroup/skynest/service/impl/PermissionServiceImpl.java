package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.SkyNestBaseException;
import com.htecgroup.skynest.exception.UserNotFoundException;
import com.htecgroup.skynest.exception.accesstype.AccessTypeNotFoundException;
import com.htecgroup.skynest.exception.buckets.BucketAlreadyDeletedException;
import com.htecgroup.skynest.exception.buckets.BucketNotFoundException;
import com.htecgroup.skynest.exception.file.FileAlreadyDeletedException;
import com.htecgroup.skynest.exception.file.FileNotFoundException;
import com.htecgroup.skynest.exception.folder.FolderAlreadyDeletedException;
import com.htecgroup.skynest.exception.folder.FolderNotFoundException;
import com.htecgroup.skynest.exception.object.ObjectAccessDeniedException;
import com.htecgroup.skynest.exception.permission.PermissionAlreadyExistsException;
import com.htecgroup.skynest.exception.permission.PermissionDoesNotExistException;
import com.htecgroup.skynest.exception.permission.UserCantRevokeTheirOwnPermissions;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.entity.*;
import com.htecgroup.skynest.model.request.PermissionEditRequest;
import com.htecgroup.skynest.model.request.PermissionGrantRequest;
import com.htecgroup.skynest.model.response.PermissionResponse;
import com.htecgroup.skynest.repository.*;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class PermissionServiceImpl implements PermissionService {

  private final CurrentUserService currentUserService;
  private final UserRepository userRepository;
  private final AccessTypeRepository accessTypeRepository;
  private final BucketRepository bucketRepository;
  private final UserObjectAccessRepository permissionRepository;
  private final FileMetadataRepository fileMetadataRepository;
  private final ModelMapper modelMapper;
  private final FolderRepository folderRepository;

  private final FileMetadataRepository fileRepository;

  @Override
  public PermissionResponse grantPermissionForBucket(
      PermissionGrantRequest permissionGrantRequest) {

    UserObjectAccessEntity permission = new UserObjectAccessEntity();

    UserEntity currentUser =
        userRepository
            .findById(currentUserService.getLoggedUser().getUuid())
            .orElseThrow(UserNotFoundException::new);

    UserEntity targetUser =
        userRepository
            .findUserByEmail(permissionGrantRequest.getGrantedToEmail())
            .orElseThrow(UserNotFoundException::new);

    AccessTypeEntity accessType =
        accessTypeRepository
            .findByName(permissionGrantRequest.getAccess())
            .orElseThrow(AccessTypeNotFoundException::new);

    BucketEntity bucket =
        bucketRepository
            .findById(permissionGrantRequest.getObjectId())
            .orElseThrow(BucketNotFoundException::new);
    checkIfBucketIsDeleted(bucket.getId());

    permission.setGrantedBy(currentUser);
    permission.setGrantedTo(targetUser);
    permission.setAccess(accessType);
    permission.setObject(bucket);
    permission.setId(
        new UserObjectAccessKey(permission.getGrantedTo().getId(), permission.getObject().getId()));

    currentUserHasPermissionForBucket(
        permissionGrantRequest.getObjectId(),
        AccessType.valueOf(accessType.getName().toUpperCase()));

    if (permissionRepository.existsById(
        new UserObjectAccessKey(targetUser.getId(), bucket.getId())))
      throw new PermissionAlreadyExistsException();

    UserObjectAccessEntity savedPermission = permissionRepository.save(permission);

    log.info(
        "User {} ({}) granted {} permission for bucket {} ({}) to user {} ({})",
        permission.getGrantedBy().getEmail(),
        permission.getGrantedBy().getId(),
        permission.getAccess().getName(),
        permission.getObject().getName(),
        permission.getObject().getId(),
        permission.getGrantedTo().getEmail(),
        permission.getGrantedTo().getId());

    return modelMapper.map(savedPermission, PermissionResponse.class);
  }

  @Override
  public void grantOwnerForObject(ObjectEntity object) {

    UserObjectAccessEntity permission = new UserObjectAccessEntity();

    UserEntity currentUser =
        userRepository
            .findById(currentUserService.getLoggedUser().getUuid())
            .orElseThrow(UserNotFoundException::new);

    AccessTypeEntity accessType =
        accessTypeRepository
            .findByName(AccessType.OWNER.getText())
            .orElseThrow(AccessTypeNotFoundException::new);

    permission.setGrantedBy(currentUser);
    permission.setGrantedTo(currentUser);
    permission.setAccess(accessType);
    permission.setObject(object);
    permission.setId(
        new UserObjectAccessKey(permission.getGrantedTo().getId(), permission.getObject().getId()));

    permissionRepository.save(permission);
  }

  @Override
  public void currentUserHasPermissionForBucket(UUID bucketId, AccessType minimumAccessType) {

    UUID currentUserId = currentUserService.getLoggedUser().getUuid();

    UserObjectAccessEntity permission =
        permissionRepository
            .findById(new UserObjectAccessKey(currentUserId, bucketId))
            .orElseThrow(ObjectAccessDeniedException::new);

    AccessType actualAccessType =
        AccessType.valueOf(permission.getAccess().getName().toUpperCase());

    if (actualAccessType.ordinal() < minimumAccessType.ordinal())
      throw new ObjectAccessDeniedException();
  }

  @Override
  public void currentUserHasPermissionForFolder(FolderEntity folder, AccessType minimumAccessType) {

    UUID currentUserId = currentUserService.getLoggedUser().getUuid();

    Optional<UserObjectAccessEntity> permission =
        permissionRepository.findById(new UserObjectAccessKey(currentUserId, folder.getId()));

    if (permission.isPresent()) {

      AccessType actualAccessType =
          AccessType.valueOf(permission.get().getAccess().getName().toUpperCase());

      if (actualAccessType.ordinal() >= minimumAccessType.ordinal()) return;
    }

    if (folder.getParentFolder() != null) {
      currentUserHasPermissionForFolder(folder.getParentFolder(), minimumAccessType);
    } else {
      currentUserHasPermissionForBucket(folder.getBucket().getId(), minimumAccessType);
    }
  }

  @Override
  public void currentUserHasPermissionForFile(
      FileMetadataEntity file, AccessType minimumAccessType) {

    UUID currentUserId = currentUserService.getLoggedUser().getUuid();

    Optional<UserObjectAccessEntity> permission =
        permissionRepository.findById(new UserObjectAccessKey(currentUserId, file.getId()));

    if (permission.isPresent()) {

      AccessType actualAccessType =
          AccessType.valueOf(permission.get().getAccess().getName().toUpperCase());

      if (actualAccessType.ordinal() >= minimumAccessType.ordinal()) return;
    }

    if (file.getParentFolder() != null) {
      currentUserHasPermissionForFolder(file.getParentFolder(), minimumAccessType);
    } else {
      currentUserHasPermissionForBucket(file.getBucket().getId(), minimumAccessType);
    }
  }

  @Override
  public List<PermissionResponse> getAllBucketPermission(UUID bucketId) {
    checkIfBucketIsDeleted(bucketId);
    currentUserHasPermissionForBucket(bucketId, AccessType.OWNER);
    List<UserObjectAccessEntity> entityList = permissionRepository.findAllByObjectId(bucketId);

    log.info("Current user accessed the permissions of the bucket with the id {}", bucketId);
    return entityList.stream()
        .map(e -> modelMapper.map(e, PermissionResponse.class))
        .collect(Collectors.toList());
  }

  private void checkIfBucketIsDeleted(UUID bucketId) {
    BucketEntity bucketEntity =
        bucketRepository.findById(bucketId).orElseThrow(BucketNotFoundException::new);
    if (bucketEntity.isDeleted()) {
      throw new BucketAlreadyDeletedException();
    }
  }

  @Override
  public PermissionResponse editPermission(
      PermissionEditRequest permissionEditRequest, UUID bucketId) {
    checkIfBucketIsDeleted(bucketId);
    currentUserHasPermissionForBucket(bucketId, AccessType.EDIT);
    UserObjectAccessEntity userObjectAccessEntity =
        permissionRepository.findByObjectId(bucketId).orElseThrow(BucketNotFoundException::new);
    UserEntity targetUser = findTargetUserForEdit(permissionEditRequest);
    checkIfTargetUserIsTheCurrentUser(targetUser);
    AccessTypeEntity accessType = findAccessTypeForEdit(permissionEditRequest);

    return setAndSavePermission(
        userObjectAccessEntity, targetUser, accessType, permissionEditRequest);
  }

  @Override
  public void revokePermission(UUID bucketId, String userEmail) {
    checkIfBucketIsDeleted(bucketId);
    currentUserHasPermissionForBucket(bucketId, AccessType.OWNER);

    UserEntity user =
        userRepository.findUserByEmail(userEmail).orElseThrow(UserNotFoundException::new);
    checkIfTargetUserIsTheCurrentUser(user);
    UserObjectAccessEntity permission =
        permissionRepository
            .findByObjectIdAndGrantedTo(bucketId, user)
            .orElseThrow(BucketNotFoundException::new);
    checkIfPermissionExist(permission);
    permissionRepository.delete(permission);
  }

  @Override
  public void revokeFilePermission(UUID fileId, String email) {
    FileMetadataEntity fileMetadataEntity =
        fileRepository.findById(fileId).orElseThrow(FileNotFoundException::new);
    checkIfBucketIsDeleted(fileMetadataEntity.getBucket().getId());
    checkIfFileIsDeleted(fileMetadataEntity);
    currentUserHasPermissionForFile(fileMetadataEntity, AccessType.OWNER);

    UserEntity user = userRepository.findUserByEmail(email).orElseThrow(UserNotFoundException::new);
    checkIfTargetUserIsTheCurrentUser(user);
    UserObjectAccessEntity permission =
        permissionRepository
            .findByObjectIdAndGrantedTo(fileId, user)
            .orElseThrow(FileNotFoundException::new);
    checkIfPermissionExist(permission);
    permissionRepository.delete(permission);
  }

  private void checkIfTargetUserIsTheCurrentUser(UserEntity user) {
    LoggedUserDto userDto = currentUserService.getLoggedUser();
    if (userDto.getUuid().equals(user.getId())) {
      throw new UserCantRevokeTheirOwnPermissions();
    }
  }

  @Override
  public List<PermissionResponse> getAllFolderPermission(UUID folderId) {
    LoggedUserDto currentUser = currentUserService.getLoggedUser();

    log.info(
        "User {} ({}) is attempting to view all permissions for folder {}...",
        currentUser.getUsername(),
        currentUser.getUuid(),
        folderId);

    FolderEntity folder =
        folderRepository.findById(folderId).orElseThrow(FolderNotFoundException::new);
    checkIfFolderIsDeleted(folder);
    currentUserHasPermissionForFolder(folder, AccessType.OWNER);
    List<UserObjectAccessEntity> entityList = permissionRepository.findAllByObjectId(folderId);

    log.info(
        "User {} ({}) viewed all permissions for folder {} ({})",
        currentUser.getUsername(),
        currentUser.getUuid(),
        folder.getName(),
        folder.getId());

    return entityList.stream()
        .map(e -> modelMapper.map(e, PermissionResponse.class))
        .collect(Collectors.toList());
  }

  private void checkIfFolderIsDeleted(FolderEntity folder) {
    if (folder.isDeleted()) {
      throw new FolderAlreadyDeletedException();
    }
  }

  private void checkIfFileIsDeleted(FileMetadataEntity fileMetadataEntity) {
    if (fileMetadataEntity.isDeleted()) {
      throw new FileAlreadyDeletedException();
    }
  }

  @Override
  public PermissionResponse grantPermissionForFile(PermissionGrantRequest permissionGrantRequest) {
    UserObjectAccessEntity permission = new UserObjectAccessEntity();

    UserEntity currentUser =
        userRepository
            .findById(currentUserService.getLoggedUser().getUuid())
            .orElseThrow(UserNotFoundException::new);

    UserEntity targetUser =
        userRepository
            .findUserByEmail(permissionGrantRequest.getGrantedToEmail())
            .orElseThrow(UserNotFoundException::new);

    AccessTypeEntity accessType =
        accessTypeRepository
            .findByName(permissionGrantRequest.getAccess())
            .orElseThrow(AccessTypeNotFoundException::new);

    FileMetadataEntity file =
        fileMetadataRepository
            .findById(permissionGrantRequest.getObjectId())
            .orElseThrow(FileNotFoundException::new);

    if (file.isDeleted()) {
      throw new FileAlreadyDeletedException();
    }

    permission.setGrantedBy(currentUser);
    permission.setGrantedTo(targetUser);
    permission.setAccess(accessType);
    permission.setObject(file);
    permission.setId(
        new UserObjectAccessKey(permission.getGrantedTo().getId(), permission.getObject().getId()));

    currentUserHasPermissionForFile(file, AccessType.OWNER);

    if (permissionRepository.existsById(
        new UserObjectAccessKey(targetUser.getId(), file.getId()))) {
      throw new PermissionAlreadyExistsException();
    }

    UserObjectAccessEntity savedPermission = permissionRepository.save(permission);

    grantViewPermissionUpstream(file, currentUser, targetUser, permissionGrantRequest);

    log.info(
        "User {} ({}) granted {} permission for file {} ({}) to user {} ({})",
        permission.getGrantedBy().getEmail(),
        permission.getGrantedBy().getId(),
        permission.getAccess().getName(),
        permission.getObject().getName(),
        permission.getObject().getId(),
        permission.getGrantedTo().getEmail(),
        permission.getGrantedTo().getId());

    return modelMapper.map(savedPermission, PermissionResponse.class);
  }

  private void grantViewPermissionUpstream(
      FileMetadataEntity file,
      UserEntity currentUser,
      UserEntity targetUser,
      PermissionGrantRequest permissionGrantRequest) {

    grantViewPermissionToAllFoldersUpstream(file.getParentFolder(), currentUser, targetUser);
    permissionGrantRequest.setObjectId(file.getBucket().getId());
    permissionGrantRequest.setAccess("view");
    try {
      grantPermissionForBucket(permissionGrantRequest);
    } catch (SkyNestBaseException ignored) {
    }
  }

  private void grantViewPermissionToAllFoldersUpstream(
      FolderEntity parent, UserEntity currentUser, UserEntity targetUser) {

    if (parent == null
        || permissionRepository.existsById(
            new UserObjectAccessKey(targetUser.getId(), parent.getId()))) {
      return;
    }

    AccessTypeEntity accessTypeView =
        accessTypeRepository
            .findByName(AccessType.VIEW.getText())
            .orElseThrow(AccessTypeNotFoundException::new);

    UserObjectAccessEntity permission = new UserObjectAccessEntity();
    permission.setGrantedBy(currentUser);
    permission.setGrantedTo(targetUser);
    permission.setAccess(accessTypeView);
    permission.setObject(parent);
    permission.setId(
        new UserObjectAccessKey(permission.getGrantedTo().getId(), permission.getObject().getId()));

    permissionRepository.save(permission);

    log.info(
        "User {} ({}) granted {} permission for folder {} ({}) to user {} ({})",
        permission.getGrantedBy().getEmail(),
        permission.getGrantedBy().getId(),
        permission.getAccess().getName(),
        permission.getObject().getName(),
        permission.getObject().getId(),
        permission.getGrantedTo().getEmail(),
        permission.getGrantedTo().getId());

    grantViewPermissionToAllFoldersUpstream(parent.getParentFolder(), currentUser, targetUser);
  }

  private void checkIfPermissionExist(UserObjectAccessEntity permission) {
    if (permission == null) {
      throw new PermissionDoesNotExistException();
    }
  }

  private UserEntity findTargetUserForEdit(PermissionEditRequest permissionEditRequest) {
    UserEntity targetUser =
        userRepository
            .findUserByEmail(permissionEditRequest.getGrantedToEmail())
            .orElseThrow(UserNotFoundException::new);
    return targetUser;
  }

  private AccessTypeEntity findAccessTypeForEdit(PermissionEditRequest permissionEditRequest) {
    AccessTypeEntity accessType =
        accessTypeRepository
            .findByName(permissionEditRequest.getAccess())
            .orElseThrow(AccessTypeNotFoundException::new);
    return accessType;
  }

  private PermissionResponse setAndSavePermission(
      UserObjectAccessEntity userObjectAccessEntity,
      UserEntity targetUser,
      AccessTypeEntity accessType,
      PermissionEditRequest permissionEditRequest) {
    userObjectAccessEntity.setGrantedTo(targetUser);
    userObjectAccessEntity.setAccess(accessType);

    modelMapper.map(permissionEditRequest, userObjectAccessEntity);
    UserObjectAccessEntity savedUserObjectAccessEntity =
        permissionRepository.save(userObjectAccessEntity);
    return modelMapper.map(savedUserObjectAccessEntity, PermissionResponse.class);
  }

  @Override
  public List<PermissionResponse> getAllFilePermissions(UUID fileId) {

    LoggedUserDto currentUser = currentUserService.getLoggedUser();

    log.info(
        "User {} ({}) is attempting to view all permissions for file {}...",
        currentUser.getUsername(),
        currentUser.getUuid(),
        fileId);

    FileMetadataEntity fileMetadata =
        fileMetadataRepository.findById(fileId).orElseThrow(FileNotFoundException::new);

    if (fileMetadata.isDeleted()) throw new FileAlreadyDeletedException();
    currentUserHasPermissionForFile(fileMetadata, AccessType.OWNER);

    List<UserObjectAccessEntity> entityList = permissionRepository.findAllByObjectId(fileId);

    log.info(
        "User {} ({}) viewed all permissions for file {} ({})",
        currentUser.getUsername(),
        currentUser.getUuid(),
        fileMetadata.getName(),
        fileMetadata.getId());

    return entityList.stream()
        .map(e -> modelMapper.map(e, PermissionResponse.class))
        .collect(Collectors.toList());
  }
}
