package com.htecgroup.skynest.annotation.validator;

import com.htecgroup.skynest.annotation.CurrentUserCanView;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.entity.RoleEntity;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.utils.LoggedUserDtoUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ViewUserValidatorTest {

  @Mock CurrentUserService currentUserService;
  @Mock CurrentUserCanView currentUserCanView;
  @InjectMocks ViewUserValidator viewUserValidator;

  @BeforeEach
  void setUp() {
    viewUserValidator.initialize(currentUserCanView);
  }

  @Test
  void isValid_WorkerAccessDenied() {

    when(currentUserCanView.role_name()).thenReturn(RoleEntity.ROLE_WORKER);
    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedWorkerUser());
    UUID uuid = UUID.randomUUID();

    Assertions.assertFalse(() -> viewUserValidator.isValid(uuid, null));

    verify(currentUserCanView, times(1)).role_name();
    verify(currentUserService, times(1)).getLoggedUser();
  }

  @Test
  void isValid_Worker() {

    LoggedUserDto currentUser = LoggedUserDtoUtil.getLoggedWorkerUser();
    when(currentUserCanView.role_name()).thenReturn(RoleEntity.ROLE_WORKER);
    when(currentUserService.getLoggedUser()).thenReturn(currentUser);
    UUID uuid = currentUser.getUuid();

    Assertions.assertTrue(() -> viewUserValidator.isValid(uuid, null));

    verify(currentUserCanView, times(1)).role_name();
    verify(currentUserService, times(1)).getLoggedUser();
  }

  @Test
  void isValid_Admin() {

    when(currentUserCanView.role_name()).thenReturn(RoleEntity.ROLE_WORKER);
    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedAdminUser());
    UUID uuid = UUID.randomUUID();

    Assertions.assertTrue(() -> viewUserValidator.isValid(uuid, null));

    verify(currentUserCanView, times(1)).role_name();
    verify(currentUserService, times(1)).getLoggedUser();
  }
}
