package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserNotFoundException;
import com.htecgroup.skynest.exception.accesstype.AccessTypeNotFoundException;
import com.htecgroup.skynest.exception.buckets.BucketAccessDeniedException;
import com.htecgroup.skynest.exception.buckets.BucketNotFoundException;
import com.htecgroup.skynest.model.entity.AccessType;
import com.htecgroup.skynest.model.entity.UserObjectAccessEntity;
import com.htecgroup.skynest.model.entity.UserObjectAccessKey;
import com.htecgroup.skynest.model.request.PermissionGrantRequest;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
  void grantPermissionForBucket() {
    //
    // when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedWorkerUser());
    //    when(userRepository.findById(LoggedUserDtoUtil.getLoggedAdminUser().getUuid()))
    //        .thenReturn(Optional.of(UserEntityUtil.getAdmin()));
    //    when(userRepository.findById(UserEntityUtil.getVerified().getId()))
    //        .thenReturn(Optional.ofNullable(UserEntityUtil.getVerified()));
    //    when(accessTypeRepository.findByName(AccessType.VIEW.getText()))
    //        .thenReturn(Optional.of(AccessTypeEntityUtil.get(AccessType.VIEW)));
    //    when(bucketRepository.findById(BucketEntityUtil.getPrivateBucket().getId()))
    //        .thenReturn(Optional.of(BucketEntityUtil.getPrivateBucket()));
    //    when(permissionRepository.findById(
    //            new UserObjectAccessKey(
    //                UserEntityUtil.getAdmin().getId(),
    // BucketEntityUtil.getPrivateBucket().getId())))
    //        .thenReturn(
    //            Optional.of(
    //                new UserObjectAccessEntity(
    //                    new UserObjectAccessKey(
    //                        UserEntityUtil.getAdmin().getId(),
    //                        BucketEntityUtil.getPrivateBucket().getId()),
    //                    UserEntityUtil.getAdmin(),
    //                    BucketEntityUtil.getPrivateBucket(),
    //                    null,
    //                    AccessTypeEntityUtil.get(AccessType.OWNER),
    //                    UserEntityUtil.getAdmin())));
    //    when(permissionRepository.save())
    //
    //    PermissionGrantRequest grantViewPermissionRequest =
    //        PermissionGrantRequestUtil.get(AccessType.VIEW);
    //    PermissionResponse permissionResponse =
    //        permissionService.grantPermissionForBucket(grantViewPermissionRequest);
    //    Assertions.assertEquals(new PermissionResponse(UserEntityUtil.getVerified()));
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
    when(userRepository.findById(UserEntityUtil.getVerified().getId()))
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
    when(userRepository.findById(UserEntityUtil.getVerified().getId()))
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
    when(userRepository.findById(UserEntityUtil.getVerified().getId()))
        .thenReturn(Optional.ofNullable(UserEntityUtil.getVerified()));
    when(accessTypeRepository.findByName(AccessType.VIEW.getText()))
        .thenReturn(Optional.of(AccessTypeEntityUtil.get(AccessType.VIEW)));
    when(bucketRepository.findById(BucketEntityUtil.getPrivateBucket().getId()))
        .thenReturn(Optional.of(BucketEntityUtil.getPrivateBucket()));
    when(permissionRepository.findById(any())).thenReturn(Optional.empty());

    PermissionGrantRequest grantViewPermissionRequest =
        PermissionGrantRequestUtil.get(AccessType.VIEW);
    Assertions.assertThrows(
        BucketAccessDeniedException.class,
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
        BucketAccessDeniedException.class,
        () -> {
          permissionService.currentUserHasPermissionForBucket(
              UserEntityUtil.getAdmin().getId(), AccessType.OWNER);
        });
  }
}
