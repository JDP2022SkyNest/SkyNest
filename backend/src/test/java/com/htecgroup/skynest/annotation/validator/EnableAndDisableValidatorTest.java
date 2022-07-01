package com.htecgroup.skynest.annotation.validator;

import com.htecgroup.skynest.model.response.UserResponse;
import com.htecgroup.skynest.service.UserService;
import com.htecgroup.skynest.utils.UserResponseUtil;
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
class EnableAndDisableValidatorTest {

  @Mock private UserService userService;

  @InjectMocks private EnableAndDisableValidator enableAndDisableValidator;

  @Test
  void when_UserIsNotAdmin_isValid_ShouldReturnTrue() {
    UserResponse userResponse = UserResponseUtil.getWorker();
    when(userService.getUser(any())).thenReturn(userResponse);

    Assertions.assertTrue(enableAndDisableValidator.isValid(UUID.randomUUID(), null));
  }

  @Test
  void when_UserIsAdmin_isValid_ShouldReturnFalse() {
    UserResponse userResponse = UserResponseUtil.getAdmin();
    when(userService.getUser(any())).thenReturn(userResponse);

    Assertions.assertFalse(enableAndDisableValidator.isValid(UUID.randomUUID(), null));
  }
}
