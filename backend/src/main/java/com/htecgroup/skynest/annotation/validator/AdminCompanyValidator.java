package com.htecgroup.skynest.annotation.validator;

import com.htecgroup.skynest.annotation.CurrentUserCanEdit;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.service.CompanyService;
import com.htecgroup.skynest.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AdminCompanyValidator implements ConstraintValidator<CurrentUserCanEdit, UUID> {
  private final CurrentUserService currentUserService;
  private final CompanyService companyService;

  @Override
  public boolean isValid(UUID uuid, ConstraintValidatorContext constraintValidatorContext) {
    LoggedUserDto loggedUserDto = currentUserService.getLoggedUser();
    UUID loggedUserCompanyId = loggedUserDto.getCompany().getId();

    UUID companyToAddId = companyService.findById(uuid).getId();

    if (!loggedUserCompanyId.equals(companyToAddId)) {
      return false;
    }
    return true;
  }
}
