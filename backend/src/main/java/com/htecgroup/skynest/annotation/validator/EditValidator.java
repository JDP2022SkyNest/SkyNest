package com.htecgroup.skynest.annotation.validator;

import com.htecgroup.skynest.annotation.CurrentUserCanEdit;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.entity.RoleEntity;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EditValidator implements ConstraintValidator<CurrentUserCanEdit, UUID> {

  private final CurrentUserService currentUserService;
  private final UserService userService;
  private CurrentUserCanEdit currentUserCanEdit;

  @Override
  public void initialize(CurrentUserCanEdit constraintAnnotation) {
    currentUserCanEdit = constraintAnnotation;
  }

  @Override
  public boolean isValid(UUID uuid, ConstraintValidatorContext context) {
    LoggedUserDto loggedUserDto = currentUserService.getLoggedUser();
    UUID loggedUserUuid = loggedUserDto.getUuid();

    String accessedUserRole = userService.getUser(uuid).getRoleName();

    if (!loggedUserUuid.equals(uuid) && accessedUserRole.equals(RoleEntity.ROLE_ADMIN)) {
      return false;
    }
    return true;
  }
}
