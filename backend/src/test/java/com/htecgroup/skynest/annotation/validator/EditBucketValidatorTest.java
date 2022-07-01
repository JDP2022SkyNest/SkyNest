package com.htecgroup.skynest.annotation.validator;

import com.htecgroup.skynest.service.BucketService;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.utils.BucketDtoUtil;
import com.htecgroup.skynest.utils.LoggedUserDtoUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EditBucketValidatorTest {

  @Mock CurrentUserService currentUserService;
  @Mock BucketService bucketService;
  @InjectMocks EditBucketValidator editBucketValidator;

  @Test
  void isValid_EditableBucket() {
    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedWorkerUser());
    when(bucketService.findBucketById(any())).thenReturn(BucketDtoUtil.getNotDeletedBucket());

    UUID uuid = UUID.randomUUID();

    Assertions.assertTrue(() -> editBucketValidator.isValid(uuid, null));
    verify(currentUserService, times(1)).getLoggedUser();
    verify(bucketService, times(1)).findBucketById(any());
  }

  @Test
  void isValid_NonEditableBucket() {
    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedWorkerUser());
    when(bucketService.findBucketById(any())).thenReturn(BucketDtoUtil.getOtherNotDeletedBucket());

    UUID uuid = UUID.randomUUID();

    Assertions.assertFalse(() -> editBucketValidator.isValid(uuid, null));
    verify(currentUserService, times(1)).getLoggedUser();
    verify(bucketService, times(1)).findBucketById(any());
  }
}
