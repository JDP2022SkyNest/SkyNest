package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserNotFoundException;
import com.htecgroup.skynest.exception.accesstype.AccessTypeNotFoundException;
import com.htecgroup.skynest.exception.buckets.BucketAccessDeniedException;
import com.htecgroup.skynest.exception.buckets.BucketNotFoundException;
import com.htecgroup.skynest.exception.permission.PermissionAlreadyExistsException;
import com.htecgroup.skynest.model.entity.*;
import com.htecgroup.skynest.model.request.PermissionGrantRequest;
import com.htecgroup.skynest.model.response.PermissionResponse;
import com.htecgroup.skynest.repository.AccessTypeRepository;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.repository.UserObjectAccessRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
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
  private final ModelMapper modelMapper;

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
            .findById(permissionGrantRequest.getGrantedTo())
            .orElseThrow(UserNotFoundException::new);

    AccessTypeEntity accessType =
        accessTypeRepository
            .findByName(permissionGrantRequest.getAccess())
            .orElseThrow(AccessTypeNotFoundException::new);

    BucketEntity bucket =
        bucketRepository
            .findById(permissionGrantRequest.getObjectId())
            .orElseThrow(BucketNotFoundException::new);

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
            .orElseThrow(BucketAccessDeniedException::new);

    AccessType actualAccessType =
        AccessType.valueOf(permission.getAccess().getName().toUpperCase());

    if (actualAccessType.ordinal() < minimumAccessType.ordinal())
      throw new BucketAccessDeniedException();
  }

  @Override
  public List<PermissionResponse> getAllBucketPermission(UUID bucketId) {
    List<UserObjectAccessEntity> entityList = permissionRepository.findAllByObjectId(bucketId);

    log.info("Current user accessed the permissions of the bucket with the id {}", bucketId);
    return entityList.stream()
        .map(e -> modelMapper.map(e, PermissionResponse.class))
        .collect(Collectors.toList());
  }
}
