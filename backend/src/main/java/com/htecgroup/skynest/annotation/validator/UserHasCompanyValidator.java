package com.htecgroup.skynest.annotation.validator;

import com.htecgroup.skynest.annotation.UserHasCompany;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserHasCompanyValidator implements ConstraintValidator<UserHasCompany, UUID> {
  private final UserService userService;

  @Override
  public boolean isValid(UUID uuid, ConstraintValidatorContext constraintValidatorContext) {
    UserDto userDto = userService.findUserById(uuid);

    return userDto.getCompany() == null;
  }
}
