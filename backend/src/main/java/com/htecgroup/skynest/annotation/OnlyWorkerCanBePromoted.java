package com.htecgroup.skynest.annotation;

import com.htecgroup.skynest.annotation.validator.PromoteValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Constraint(validatedBy = PromoteValidator.class)
public @interface OnlyWorkerCanBePromoted {
  String message() default "Can't promote user that is not a worker.";

  Class<?> groups()[] default {};

  Class<? extends Payload>[] payload() default {};
}
