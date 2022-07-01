package com.htecgroup.skynest.annotation.validator;

import com.htecgroup.skynest.annotation.AdminUserAndUserHaveSameCompany;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AdminAndUserInSameCompanyValidator
    implements ConstraintValidator<AdminUserAndUserHaveSameCompany, UUID> {

  private final CurrentUserService currentUserService;
  private final UserService userService;

  @Override
  public boolean isValid(UUID uuid, ConstraintValidatorContext constraintValidatorContext) {
    LoggedUserDto loggedAdminDto = currentUserService.getLoggedUser();
    UserDto userDto = userService.findUserById(uuid);

    if (loggedAdminDto.getCompany() == null || userDto.getCompany() == null) {
      return false;
    }
    UUID loggedAdminCompanyId = loggedAdminDto.getCompany().getId();
    UUID userToRemoveCompanyId = userDto.getCompany().getId();

    return loggedAdminCompanyId.equals(userToRemoveCompanyId);
  }
}
