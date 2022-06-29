package com.htecgroup.skynest.annotation;

import com.htecgroup.skynest.annotation.validator.DemoteValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Constraint(validatedBy = DemoteValidator.class)
public @interface OnlyManagerCanBeDemoted {
  String message() default "Can't demote user that is not a manager.";

  Class<?> groups()[] default {};

  Class<? extends Payload>[] payload() default {};
}
