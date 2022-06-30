package com.htecgroup.skynest.annotation;

import com.htecgroup.skynest.annotation.validator.EditBucketValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Constraint(validatedBy = EditBucketValidator.class)
public @interface CanBucketBeEdited {

  String message() default "Current user can't edit this bucket";

  Class<?> groups()[] default {};

  Class<? extends Payload>[] payload() default {};
}
