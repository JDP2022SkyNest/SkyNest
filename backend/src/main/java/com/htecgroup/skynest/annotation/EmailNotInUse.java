package com.htecgroup.skynest.annotation;

import com.htecgroup.skynest.annotation.validator.EmailNotInUseValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = EmailNotInUseValidator.class)
public @interface EmailNotInUse {
  String message() default "User is already in a company";

  Class<?> groups()[] default {};

  Class<? extends Payload>[] payload() default {};
}
