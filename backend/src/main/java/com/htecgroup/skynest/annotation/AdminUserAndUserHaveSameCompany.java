package com.htecgroup.skynest.annotation;

import com.htecgroup.skynest.annotation.validator.AdminAndUserInSameCompanyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Constraint(validatedBy = AdminAndUserInSameCompanyValidator.class)
public @interface AdminUserAndUserHaveSameCompany {
  String message() default "Admin and user are not in the same company";

  Class<?> groups()[] default {};

  Class<? extends Payload>[] payload() default {};
}
