package com.htecgroup.skynest.annotation.validator;

import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.service.UserService;
import com.htecgroup.skynest.utils.LoggedUserDtoUtil;
import com.htecgroup.skynest.utils.UserDtoUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminAndUserInSameCompanyValidatorTest {
  @Mock CurrentUserService currentUserService;
  @Mock UserService userService;
  @InjectMocks AdminAndUserInSameCompanyValidator adminAndUserInSameCompanyValidator;

  @Test
  void when_EverythingFine_isValid_ShouldReturnTrue() {
    when(currentUserService.getLoggedUser())
        .thenReturn(LoggedUserDtoUtil.getLoggedAdminWithCompany());
    when(userService.findUserById(any())).thenReturn(UserDtoUtil.getUserWithCompany());
    UUID uuid = UUID.randomUUID();

    Assertions.assertTrue(adminAndUserInSameCompanyValidator.isValid(uuid, null));
  }

  @Test
  void when_LoggedUserHasNoCompany_isValid_ShouldReturnFalse() {
    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedAdminUser());
    when(userService.findUserById(any())).thenReturn(UserDtoUtil.getUserWithCompany());
    UUID uuid = UUID.randomUUID();

    Assertions.assertFalse(adminAndUserInSameCompanyValidator.isValid(uuid, null));
  }

  @Test
  void when_LoggedUserHasNoCompany_isValid_ShouldReturnTrue() {
    when(currentUserService.getLoggedUser())
        .thenReturn(LoggedUserDtoUtil.getLoggedAdminWithCompany());
    when(userService.findUserById(any())).thenReturn(UserDtoUtil.getVerified());
    UUID uuid = UUID.randomUUID();

    Assertions.assertFalse(adminAndUserInSameCompanyValidator.isValid(uuid, null));
  }
}
