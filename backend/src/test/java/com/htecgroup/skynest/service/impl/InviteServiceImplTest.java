package com.htecgroup.skynest.service.impl;

import com.auth0.jwt.algorithms.Algorithm;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.service.EmailService;
import com.htecgroup.skynest.util.JwtUtils;
import com.htecgroup.skynest.utils.LoggedUserDtoUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class InviteServiceImplTest {

  @Mock CurrentUserService currentUserService;
  @Mock EmailService emailService;

  @Spy @InjectMocks private InviteServiceImpl inviteService;

  @Test
  void sendRegistrationInvite() {
    JwtUtils.ALGORITHM = Algorithm.HMAC512("test secret");

    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedAdminUser());
    doNothing().when(emailService).send(any());

    verify(emailService, times(1)).send(any());
  }
}
