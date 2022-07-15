package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.annotation.EmailNotInUse;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.email.Email;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.service.EmailService;
import com.htecgroup.skynest.service.InviteService;
import com.htecgroup.skynest.util.EmailUtil;
import com.htecgroup.skynest.util.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Log4j2
@Validated
@AllArgsConstructor
public class InviteServiceImpl implements InviteService {

  private final CurrentUserService currentUserService;
  private final EmailService emailService;

  @Override
  public void sendRegistrationInvite(@EmailNotInUse String newUserEmail) {

    LoggedUserDto loggedUserDto = currentUserService.getLoggedUser();

    String token =
        JwtUtils.generateRegistrationInviteToken(
            loggedUserDto.getName(), loggedUserDto.getCompany().getName());
    Email email = EmailUtil.createRegistrationInviteEmail(loggedUserDto, newUserEmail, token);

    emailService.send(email);
    log.info(
        "A registration invite was sent to {} by {} {}",
        newUserEmail,
        loggedUserDto.getName(),
        loggedUserDto.getSurname());
  }
}
