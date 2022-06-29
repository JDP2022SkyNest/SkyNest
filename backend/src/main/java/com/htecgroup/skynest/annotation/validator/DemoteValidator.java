package com.htecgroup.skynest.annotation.validator;

import com.htecgroup.skynest.annotation.OnlyManagerCanBeDemoted;
import com.htecgroup.skynest.model.entity.RoleEntity;
import com.htecgroup.skynest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DemoteValidator implements ConstraintValidator<OnlyManagerCanBeDemoted, UUID> {
  private final UserService userService;

  @Override
  public boolean isValid(UUID uuid, ConstraintValidatorContext constraintValidatorContext) {
    String accessedUserRole = userService.getUser(uuid).getRoleName();
    return accessedUserRole.equals(RoleEntity.ROLE_MANAGER);
  }
}
