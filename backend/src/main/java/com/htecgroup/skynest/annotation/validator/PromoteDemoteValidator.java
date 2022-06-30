package com.htecgroup.skynest.annotation.validator;

import com.htecgroup.skynest.annotation.CanPromoteDemoteManagerWorker;
import com.htecgroup.skynest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PromoteDemoteValidator
    implements ConstraintValidator<CanPromoteDemoteManagerWorker, UUID> {
  private final UserService userService;

  private CanPromoteDemoteManagerWorker canPromoteDemoteManagerWorker;

  @Override
  public void initialize(CanPromoteDemoteManagerWorker constraintAnnotation) {
    canPromoteDemoteManagerWorker = constraintAnnotation;
  }

  @Override
  public boolean isValid(UUID uuid, ConstraintValidatorContext constraintValidatorContext) {
    String roleName = canPromoteDemoteManagerWorker.role_name();

    String accessedUserRole = userService.getUser(uuid).getRoleName();
    return accessedUserRole.equals(roleName);
  }
}
