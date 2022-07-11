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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
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
            .findById(permissionGrantRequest.getBucketId())
            .orElseThrow(BucketNotFoundException::new);

    permission.setGrantedBy(currentUser);
    permission.setGrantedTo(targetUser);
    permission.setAccess(accessType);
    permission.setObject(bucket);
    permission.setId(
        new UserObjectAccessKey(permission.getGrantedTo().getId(), permission.getObject().getId()));

    currentUserHasPermissionForBucket(
        permissionGrantRequest.getBucketId(),
        AccessType.valueOf(accessType.getName().toUpperCase()));

    if (permissionRepository.existsById(
        new UserObjectAccessKey(targetUser.getId(), bucket.getId())))
      throw new PermissionAlreadyExistsException();

    UserObjectAccessEntity savedPermission = permissionRepository.save(permission);

    return modelMapper.map(savedPermission, PermissionResponse.class);
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
}
