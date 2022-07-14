package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.register.EmailAlreadyInUseException;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.email.Email;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.service.EmailService;
import com.htecgroup.skynest.service.InviteService;
import com.htecgroup.skynest.util.EmailUtil;
import com.htecgroup.skynest.util.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InviteServiceImpl implements InviteService {

  private final CurrentUserService currentUserService;
  private final EmailService emailService;
  private final UserRepository userRepository;

  @Override
  public void sendRegistrationInvite(String newUserEmail) {

    if (userRepository.existsByEmail(newUserEmail)) {
      throw new EmailAlreadyInUseException();
    }

    LoggedUserDto loggedUserDto = currentUserService.getLoggedUser();

    String token =
        JwtUtils.generateRegistrationInviteToken(
            loggedUserDto.getName(), loggedUserDto.getCompany().getName());
    Email email = EmailUtil.createRegistrationInviteEmail(loggedUserDto, newUserEmail, token);

    emailService.send(email);
  }
}
