package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserNotFoundException;
import com.htecgroup.skynest.exception.accesstype.AccessTypeNotFoundException;
import com.htecgroup.skynest.exception.buckets.BucketNotFoundException;
import com.htecgroup.skynest.exception.object.ObjectAccessDeniedException;
import com.htecgroup.skynest.model.entity.*;
import com.htecgroup.skynest.model.request.PermissionEditRequest;
import com.htecgroup.skynest.model.request.PermissionGrantRequest;
import com.htecgroup.skynest.model.response.PermissionResponse;
import com.htecgroup.skynest.repository.AccessTypeRepository;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.repository.UserObjectAccessRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.utils.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermissionServiceImplTest {

  @Mock private CurrentUserService currentUserService;
  @Mock private UserRepository userRepository;
  @Mock private AccessTypeRepository accessTypeRepository;
  @Mock private BucketRepository bucketRepository;

  @Mock private UserObjectAccessRepository permissionRepository;
  @Spy private ModelMapper modelMapper;

  @InjectMocks private PermissionServiceImpl permissionService;

  @BeforeEach
  void setUp() {}

  @Test
  void when_getAllPermissionsForBucket_shouldReturnAllPermissions() {
    List<UserObjectAccessEntity> userObjectAccessEntityList =
        Collections.singletonList(UserObjectAccessEntityUtil.getUserObjectAccess());
    BucketEntity bucketEntity = BucketEntityUtil.getPrivateBucket();
    UserObjectAccessEntity userObjectAccessEntity =
        UserObjectAccessEntityUtil.getUserObjectAccess();
    UUID objectId = bucketEntity.getId();
    when(bucketRepository.findById(any())).thenReturn(Optional.of(bucketEntity));
    when(permissionRepository.findById(any())).thenReturn(Optional.of(userObjectAccessEntity));
    when(permissionRepository.findAllByObjectId(objectId)).thenReturn(userObjectAccessEntityList);
    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedWorkerUser());

    List<UserObjectAccessEntity> expectedResponse = new ArrayList<>(userObjectAccessEntityList);

    List<PermissionResponse> actualResponse = permissionService.getAllBucketPermission(objectId);

    Assertions.assertEquals(expectedResponse.size(), actualResponse.size());
    verify(permissionRepository, times(1)).findAllByObjectId(objectId);
    Assertions.assertEquals(
        expectedResponse.get(0).getObject().getId(), actualResponse.get(0).getObjectId());
  }

  @Test
  void grantPermissionForBucket_ThrowsException_WhenRequestedUserDoesNotExist() {
    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedWorkerUser());
    when(userRepository.findById(any())).thenReturn(Optional.empty());
    PermissionGrantRequest grantViewPermissionRequest =
        PermissionGrantRequestUtil.get(AccessType.VIEW);
    Assertions.assertThrows(
        UserNotFoundException.class,
        () -> {
          permissionService.grantPermissionForBucket(grantViewPermissionRequest);
        });
  }

  @Test
  void grantPermissionForBucket_ThrowsException_WhenAccessTypeDoesNotExist() {
    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedWorkerUser());
    when(userRepository.findById(LoggedUserDtoUtil.getLoggedAdminUser().getUuid()))
        .thenReturn(Optional.of(UserEntityUtil.getAdmin()));
    when(userRepository.findUserByEmail(UserEntityUtil.getVerified().getEmail()))
        .thenReturn(Optional.ofNullable(UserEntityUtil.getVerified()));
    when(accessTypeRepository.findByName(any())).thenReturn(Optional.empty());

    PermissionGrantRequest grantViewPermissionRequest =
        PermissionGrantRequestUtil.get(AccessType.VIEW);
    Assertions.assertThrows(
        AccessTypeNotFoundException.class,
        () -> {
          permissionService.grantPermissionForBucket(grantViewPermissionRequest);
        });
  }

  @Test
  void grantPermissionForBucket_ThrowsException_WhenBucketDoesNotExist() {
    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedWorkerUser());
    when(userRepository.findById(LoggedUserDtoUtil.getLoggedAdminUser().getUuid()))
        .thenReturn(Optional.of(UserEntityUtil.getAdmin()));
    when(userRepository.findUserByEmail(UserEntityUtil.getVerified().getEmail()))
        .thenReturn(Optional.ofNullable(UserEntityUtil.getVerified()));
    when(accessTypeRepository.findByName(AccessType.VIEW.getText()))
        .thenReturn(Optional.of(AccessTypeEntityUtil.get(AccessType.VIEW)));
    when(bucketRepository.findById(any())).thenReturn(Optional.empty());

    PermissionGrantRequest grantViewPermissionRequest =
        PermissionGrantRequestUtil.get(AccessType.VIEW);
    Assertions.assertThrows(
        BucketNotFoundException.class,
        () -> {
          permissionService.grantPermissionForBucket(grantViewPermissionRequest);
        });
  }

  @Test
  void grantPermissionForBucket_ThrowsException_WhenCurrentUserCannotGrantPermissionForBucket() {
    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedWorkerUser());
    when(userRepository.findById(LoggedUserDtoUtil.getLoggedAdminUser().getUuid()))
        .thenReturn(Optional.of(UserEntityUtil.getAdmin()));
    when(userRepository.findUserByEmail(UserEntityUtil.getVerified().getEmail()))
        .thenReturn(Optional.ofNullable(UserEntityUtil.getVerified()));
    when(accessTypeRepository.findByName(AccessType.VIEW.getText()))
        .thenReturn(Optional.of(AccessTypeEntityUtil.get(AccessType.VIEW)));
    when(bucketRepository.findById(BucketEntityUtil.getPrivateBucket().getId()))
        .thenReturn(Optional.of(BucketEntityUtil.getPrivateBucket()));
    when(permissionRepository.findById(any())).thenReturn(Optional.empty());

    PermissionGrantRequest grantViewPermissionRequest =
        PermissionGrantRequestUtil.get(AccessType.VIEW);
    Assertions.assertThrows(
        ObjectAccessDeniedException.class,
        () -> {
          permissionService.grantPermissionForBucket(grantViewPermissionRequest);
        });
  }

  @Test
  void currentUserHasPermissionForBucket_ThrowsException_WhenUserDoesNotHaveSufficientPermission() {

    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedWorkerUser());
    UserObjectAccessKey userObjectAccessKey =
        new UserObjectAccessKey(
            UserEntityUtil.getAdmin().getId(), BucketEntityUtil.getPrivateBucket().getId());
    UserObjectAccessEntity userObjectAccess =
        new UserObjectAccessEntity(
            userObjectAccessKey,
            UserEntityUtil.getAdmin(),
            BucketEntityUtil.getPrivateBucket(),
            null,
            AccessTypeEntityUtil.get(AccessType.VIEW),
            UserEntityUtil.getAdmin());

    when(permissionRepository.findById(any())).thenReturn(Optional.of(userObjectAccess));

    Assertions.assertThrows(
        ObjectAccessDeniedException.class,
        () -> {
          permissionService.currentUserHasPermissionForBucket(
              UserEntityUtil.getAdmin().getId(), AccessType.OWNER);
        });
  }

  @Test
  void editPermission() {
    UserObjectAccessEntity expectedUserObjectAccessEntity =
        UserObjectAccessEntityUtil.getUserObjectAccess();
    UserEntity userEntity = UserEntityUtil.getVerified();
    AccessTypeEntity accessType = AccessTypeEntityUtil.get(AccessType.EDIT);
    BucketEntity bucketEntity = BucketEntityUtil.getPrivateBucket();
    UserObjectAccessEntity userObjectAccessEntity =
        UserObjectAccessEntityUtil.getUserObjectAccess();
    when(bucketRepository.findById(any())).thenReturn(Optional.of(bucketEntity));
    when(permissionRepository.findById(any())).thenReturn(Optional.of(userObjectAccessEntity));
    when(permissionRepository.findByObjectId(any()))
        .thenReturn(Optional.of(expectedUserObjectAccessEntity));
    when(userRepository.findUserByEmail(any())).thenReturn(Optional.of(userEntity));
    when(accessTypeRepository.findByName(any())).thenReturn(Optional.of(accessType));
    when(permissionRepository.save(any())).thenReturn(expectedUserObjectAccessEntity);
    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedWorkerUser());

    PermissionEditRequest permissionEditRequest = PermissionEditRequestUtil.get();
    PermissionResponse actualPermissionResponse =
        permissionService.editPermission(
            permissionEditRequest, expectedUserObjectAccessEntity.getObject().getId());

    verify(permissionRepository, times(1))
        .findByObjectId(expectedUserObjectAccessEntity.getObject().getId());
    verify(permissionRepository, times(1)).save(expectedUserObjectAccessEntity);
    Assertions.assertEquals(
        expectedUserObjectAccessEntity.getObject().getId(), actualPermissionResponse.getObjectId());
    Assertions.assertEquals(
        expectedUserObjectAccessEntity.getAccess().getName(),
        actualPermissionResponse.getAccessName());
  }

  @Test
  void when_revokePermission_DeleteFromDatabase() {
    UserObjectAccessEntity expectedUserObjectAccessEntity =
        UserObjectAccessEntityUtil.getUserObjectAccess();
    UserEntity userEntity = UserEntityUtil.getVerified();
    BucketEntity bucketEntity = BucketEntityUtil.getPrivateBucket();
    UUID bucketId = bucketEntity.getId();
    UserObjectAccessEntity userObjectAccessEntity =
        UserObjectAccessEntityUtil.getUserObjectAccess();
    when(bucketRepository.findById(any())).thenReturn(Optional.of(bucketEntity));
    when(permissionRepository.findById(any())).thenReturn(Optional.of(userObjectAccessEntity));
    when(userRepository.findUserByEmail(any())).thenReturn(Optional.of(userEntity));
    when(permissionRepository.findByObjectIdAndGrantedTo(bucketId, userEntity))
        .thenReturn(Optional.of(expectedUserObjectAccessEntity));
    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedWorkerUser());

    permissionService.revokePermission(bucketId, userEntity.getEmail());
    verify(permissionRepository, times(1)).findByObjectIdAndGrantedTo(bucketId, userEntity);
    verify(userRepository, times(1)).findUserByEmail(userEntity.getEmail());
  }
}
