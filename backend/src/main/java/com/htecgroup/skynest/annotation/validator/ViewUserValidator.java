package com.htecgroup.skynest.annotation.validator;

import com.htecgroup.skynest.annotation.CurrentUserCanView;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ViewUserValidator implements ConstraintValidator<CurrentUserCanView, UUID> {

  private final CurrentUserService currentUserService;
  private CurrentUserCanView currentUserCanView;

  @Override
  public void initialize(CurrentUserCanView constraintAnnotation) {
    currentUserCanView = constraintAnnotation;
  }

  @Override
  public boolean isValid(UUID uuid, ConstraintValidatorContext context) {
    String roleName = currentUserCanView.role_name();
    LoggedUserDto loggedUserDto = currentUserService.getLoggedUser();
    UUID loggedUserUuid = loggedUserDto.getUuid();

    if (!loggedUserUuid.equals(uuid) && loggedUserDto.hasRole(roleName)) {
      return false;
    }
    return true;
  }
}
