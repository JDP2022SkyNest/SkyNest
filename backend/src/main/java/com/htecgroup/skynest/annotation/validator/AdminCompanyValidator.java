package com.htecgroup.skynest.annotation.validator;

import com.htecgroup.skynest.annotation.AddUserInAdminCompany;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AdminCompanyValidator implements ConstraintValidator<AddUserInAdminCompany, UUID> {
  private final CurrentUserService currentUserService;

  @Override
  public boolean isValid(UUID companyId, ConstraintValidatorContext constraintValidatorContext) {
    LoggedUserDto loggedUserDto = currentUserService.getLoggedUser();

    if (loggedUserDto.getCompany() == null) {
      return false;
    }
    UUID loggedUserCompanyId = loggedUserDto.getCompany().getId();

    return loggedUserCompanyId.equals(companyId);
  }
}
