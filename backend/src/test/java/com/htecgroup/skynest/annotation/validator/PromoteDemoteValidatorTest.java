package com.htecgroup.skynest.annotation.validator;

import com.htecgroup.skynest.annotation.CanPromoteDemoteManagerWorker;
import com.htecgroup.skynest.model.response.UserResponse;
import com.htecgroup.skynest.service.UserService;
import com.htecgroup.skynest.utils.UserResponseUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PromoteDemoteValidatorTest {

  @Mock private UserService userService;

  @Mock CanPromoteDemoteManagerWorker canPromoteDemoteManagerWorker;

  @InjectMocks PromoteDemoteValidator promoteDemoteValidator;

  @BeforeEach
  void setUp() {
    promoteDemoteValidator.initialize(canPromoteDemoteManagerWorker);
  }

  @Test
  void when_ManagerIsGettingPromoted_isValid_ShouldReturnFalse() {
    UserResponse userResponse = UserResponseUtil.getManager();
    when(canPromoteDemoteManagerWorker.role_name()).thenReturn("role_worker");
    when(userService.getUser(any())).thenReturn(userResponse);

    Assertions.assertFalse(promoteDemoteValidator.isValid(UUID.randomUUID(), null));
  }

  @Test
  void when_ManagerIsGettingDemoted_isValid_ShouldReturnTrue() {
    UserResponse userResponse = UserResponseUtil.getManager();
    when(canPromoteDemoteManagerWorker.role_name()).thenReturn("role_manager");
    when(userService.getUser(any())).thenReturn(userResponse);

    Assertions.assertTrue(promoteDemoteValidator.isValid(UUID.randomUUID(), null));
  }

  @Test
  void when_WorkerIsGettingPromoted_isValid_ShouldReturnTrue() {
    UserResponse userResponse = UserResponseUtil.getWorker();
    when(canPromoteDemoteManagerWorker.role_name()).thenReturn("role_worker");
    when(userService.getUser(any())).thenReturn(userResponse);

    Assertions.assertTrue(promoteDemoteValidator.isValid(UUID.randomUUID(), null));
  }

  @Test
  void when_WorkerIsGettingDemoted_isValid_ShouldReturnFalse() {
    UserResponse userResponse = UserResponseUtil.getWorker();
    when(canPromoteDemoteManagerWorker.role_name()).thenReturn("role_manager");
    when(userService.getUser(any())).thenReturn(userResponse);

    Assertions.assertFalse(promoteDemoteValidator.isValid(UUID.randomUUID(), null));
  }
}
