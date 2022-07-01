package com.htecgroup.skynest.annotation.validator;

import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.service.UserService;
import com.htecgroup.skynest.utils.LoggedUserDtoUtil;
import com.htecgroup.skynest.utils.UserResponseUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserValidatorTest {

  @Mock CurrentUserService currentUserService;
  @Mock UserService userService;
  @InjectMocks DeleteUserValidator deleteUserValidator;

  @Test
  void isValid_AdminAccessDenied() {

    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedAdminUser());
    when(userService.getUser(any())).thenReturn(UserResponseUtil.getAdmin());
    UUID uuid = UUID.randomUUID();

    Assertions.assertFalse(() -> deleteUserValidator.isValid(uuid, null));

    verify(currentUserService, times(1)).getLoggedUser();
    verify(userService, times(1)).getUser(any());
  }

  @Test
  void isValid_AdminEditHisDetails() {

    LoggedUserDto currentUser = LoggedUserDtoUtil.getLoggedAdminUser();
    when(currentUserService.getLoggedUser()).thenReturn(currentUser);
    when(userService.getUser(any())).thenReturn(UserResponseUtil.getAdmin());
    UUID uuid = currentUser.getUuid();

    Assertions.assertTrue(() -> deleteUserValidator.isValid(uuid, null));

    verify(currentUserService, times(1)).getLoggedUser();
    verify(userService, times(1)).getUser(any());
  }
}
