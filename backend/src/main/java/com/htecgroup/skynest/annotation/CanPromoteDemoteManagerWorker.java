package com.htecgroup.skynest.annotation;

import com.htecgroup.skynest.annotation.validator.PromoteDemoteValidator;
import com.htecgroup.skynest.model.entity.RoleEntity;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Constraint(validatedBy = PromoteDemoteValidator.class)
public @interface CanPromoteDemoteManagerWorker {
  String message() default "Can't promote/demote user.";

  String role_name() default RoleEntity.ROLE_WORKER;

  Class<?> groups()[] default {};

  Class<? extends Payload>[] payload() default {};
}
