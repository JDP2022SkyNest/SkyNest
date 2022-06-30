package com.htecgroup.skynest.annotation;

import com.htecgroup.skynest.annotation.validator.AdminCompanyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Constraint(validatedBy = AdminCompanyValidator.class)
public @interface AdminAndUserInSameCompany {
  String message() default "Admin and user company don't match";

  Class<?> groups()[] default {};

  Class<? extends Payload>[] payload() default {};
}
