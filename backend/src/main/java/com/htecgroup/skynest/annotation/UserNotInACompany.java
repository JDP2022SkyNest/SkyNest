package com.htecgroup.skynest.annotation;

import com.htecgroup.skynest.annotation.validator.UserNotInACompanyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Constraint(validatedBy = UserNotInACompanyValidator.class)
public @interface UserNotInACompany {
  String message() default "User already has a company";

  Class<?> groups()[] default {};

  Class<? extends Payload>[] payload() default {};
}
