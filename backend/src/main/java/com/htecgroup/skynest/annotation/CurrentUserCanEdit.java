package com.htecgroup.skynest.annotation;

import com.htecgroup.skynest.annotation.validator.EditValidator;
import com.htecgroup.skynest.model.entity.RoleEntity;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Constraint(validatedBy = EditValidator.class)
public @interface CurrentUserCanEdit {

  String message() default "Access denied";

  String role_name() default RoleEntity.ROLE_ADMIN;

  Class<?> groups()[] default {};

  Class<? extends Payload>[] payload() default {};
}
