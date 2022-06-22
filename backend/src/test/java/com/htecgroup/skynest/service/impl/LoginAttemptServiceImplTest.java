package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.entity.ActionAttemptEntity;
import com.htecgroup.skynest.properties.LoginAttemptProperties;
import com.htecgroup.skynest.repository.ActionAttemptRepository;
import com.htecgroup.skynest.service.UserService;
import com.htecgroup.skynest.utils.UserDtoUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginAttemptServiceImplTest {

  @Mock private UserService userService;
  @Mock private ActionAttemptRepository actionAttemptRepository;
  @Mock private LoginAttemptProperties loginAttemptProperties;

  @Captor ArgumentCaptor<ActionAttemptEntity> captorActionAttemptEntity;

  @InjectMocks LoginAttemptServiceImpl loginAttemptService;

  @Test
  void when_loginAttemptsIsEqualWithMax_ShouldReturnTrue() {
    UserDto userDto = UserDtoUtil.getNotVerified();
    when(userService.findUserByEmail(anyString())).thenReturn(userDto);
    when(actionAttemptRepository.countByUserIdAndAction(any(), anyString())).thenReturn(5L);
    when(loginAttemptProperties.getMaxAttemptsPerInterval()).thenReturn(5);

    assertTrue(loginAttemptService.hasTooManyAttempts(userDto.getEmail()));
  }

  @Test
  void when_loginAttemptsIsLessThanMax_ShouldReturnFalse() {
    UserDto userDto = UserDtoUtil.getNotVerified();
    when(userService.findUserByEmail(anyString())).thenReturn(userDto);
    when(actionAttemptRepository.countByUserIdAndAction(any(), anyString())).thenReturn(1L);
    when(loginAttemptProperties.getMaxAttemptsPerInterval()).thenReturn(5);

    assertFalse(loginAttemptService.hasTooManyAttempts(userDto.getEmail()));
  }

  @Test
  void when_loginAttemptsIsGreaterThanMax_ShouldReturnTrue() {
    UserDto userDto = UserDtoUtil.getNotVerified();
    when(userService.findUserByEmail(anyString())).thenReturn(userDto);
    when(actionAttemptRepository.countByUserIdAndAction(any(), anyString())).thenReturn(6L);
    when(loginAttemptProperties.getMaxAttemptsPerInterval()).thenReturn(5);

    assertTrue(loginAttemptService.hasTooManyAttempts(userDto.getEmail()));
  }

  @Test
  void when_saveUnsuccessfulAttempt_ShouldSetPropertiesAndSaveAttempt() {
    UserDto userDto = UserDtoUtil.getNotVerified();
    when(userService.findUserByEmail(anyString())).thenReturn(userDto);
    when(loginAttemptProperties.getIntervalInSeconds()).thenReturn(0);
    loginAttemptService.saveUnsuccessfulAttempt(userDto.getEmail());
    Mockito.verify(actionAttemptRepository).save(captorActionAttemptEntity.capture(), anyInt());

    ActionAttemptEntity actionAttemptEntity = captorActionAttemptEntity.getValue();
    assertEquals(userDto.getId(), actionAttemptEntity.getUserId());
    assertEquals(ActionAttemptEntity.UNSUCCESSFUL_LOGIN_ATTEMPT, actionAttemptEntity.getAction());
  }
}
