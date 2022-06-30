package com.htecgroup.skynest.annotation.validator;

import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.service.UserService;
import com.htecgroup.skynest.utils.UserDtoUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserHasCompanyValidatorTest {
  @Mock private UserService userService;

  @InjectMocks private UserHasCompanyValidator userHasCompanyValidator;

  @Test
  void when_UserWithoutCompany_isValid_ShouldReturnTrue() {
    UserDto userDto = UserDtoUtil.getVerified();
    when(userService.findUserById(any())).thenReturn(userDto);

    Assertions.assertTrue(userHasCompanyValidator.isValid(userDto.getId(), null));
  }

  @Test
  void when_UserHasCompany_isValid_ShouldReturnFalse() {
    UserDto userDto = UserDtoUtil.getUserWithCompany();
    when(userService.findUserById(any())).thenReturn(userDto);

    Assertions.assertFalse(userHasCompanyValidator.isValid(userDto.getId(), null));
  }
}
