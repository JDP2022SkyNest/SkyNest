package com.htecgroup.skynest.annotation.validator;

import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.utils.LoggedUserDtoUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminCompanyValidatorTest {

  @Mock private CurrentUserService currentUserService;

  @InjectMocks AdminCompanyValidator adminCompanyValidator;

  @Test
  void when_EverythingFine_isValid_ShouldReturnTrue() {
    LoggedUserDto loggedAdminWithCompany = LoggedUserDtoUtil.getLoggedAdminWithCompany();
    when(currentUserService.getLoggedUser()).thenReturn(loggedAdminWithCompany);

    Assertions.assertTrue(
        adminCompanyValidator.isValid(loggedAdminWithCompany.getCompany().getId(), null));
  }

  @Test
  void when_AdminHasNoCompany_isValid_ShouldReturnFalse() {
    LoggedUserDto loggedAdminWithoutCompany = LoggedUserDtoUtil.getLoggedAdminUser();
    when(currentUserService.getLoggedUser()).thenReturn(loggedAdminWithoutCompany);

    Assertions.assertFalse(adminCompanyValidator.isValid(UUID.randomUUID(), null));
  }

  @Test
  void when_AdminAndUserHaveDifferentCompanies_isValid_ShouldReturnFalse() {
    LoggedUserDto loggedAdminWithCompany = LoggedUserDtoUtil.getLoggedAdminWithCompany();
    when(currentUserService.getLoggedUser()).thenReturn(loggedAdminWithCompany);

    Assertions.assertFalse(adminCompanyValidator.isValid(UUID.randomUUID(), null));
  }
}
