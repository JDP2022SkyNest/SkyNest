package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.entity.ActionAttemptEntity;
import com.htecgroup.skynest.properties.LoginAttemptProperties;
import com.htecgroup.skynest.repository.ActionAttemptRepository;
import com.htecgroup.skynest.service.LoginAttemptService;
import com.htecgroup.skynest.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class LoginAttemptServiceImpl implements LoginAttemptService {

  private UserService userService;
  private ActionAttemptRepository actionAttemptRepository;

  private LoginAttemptProperties loginAttemptProperties;

  @Override
  public boolean hasTooManyAttempts(String email) {

    UserDto userDto = userService.findUserByEmail(email);

    long countAttempts =
        actionAttemptRepository.countByUserIdAndAction(
            userDto.getId(), ActionAttemptEntity.UNSUCCESSFUL_LOGIN_ATTEMPT);

    return countAttempts >= loginAttemptProperties.getMaxAttemptsPerInterval();
  }

  @Override
  public void saveUnsuccessfulAttempt(String email) {

    UserDto userDto = userService.findUserByEmail(email);

    ActionAttemptEntity newUnsuccessfulAttempt = new ActionAttemptEntity();
    newUnsuccessfulAttempt.setUserId(userDto.getId());
    newUnsuccessfulAttempt.setAction(ActionAttemptEntity.UNSUCCESSFUL_LOGIN_ATTEMPT);

    actionAttemptRepository.save(
        newUnsuccessfulAttempt, loginAttemptProperties.getIntervalInSeconds());
  }
}
