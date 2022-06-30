package com.htecgroup.skynest.annotation;

import com.htecgroup.skynest.annotation.validator.UserHasCompanyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Constraint(validatedBy = UserHasCompanyValidator.class)
public @interface UserHasCompany {
  String message() default "User already has company";

  Class<?> groups()[] default {};

  Class<? extends Payload>[] payload() default {};
}
