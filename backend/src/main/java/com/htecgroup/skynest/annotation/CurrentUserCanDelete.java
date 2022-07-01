package com.htecgroup.skynest.annotation;

import com.htecgroup.skynest.annotation.validator.DeleteUserValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Constraint(validatedBy = DeleteUserValidator.class)
public @interface CurrentUserCanDelete {

  String message() default "Access denied";

  Class<?> groups()[] default {};

  Class<? extends Payload>[] payload() default {};
}
