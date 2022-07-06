package com.htecgroup.skynest.annotation;

import com.htecgroup.skynest.annotation.validator.ParentFolderIsInTheSameBucketValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Constraint(validatedBy = ParentFolderIsInTheSameBucketValidator.class)
public @interface ParentFolderIsInTheSameBucket {
  String message() default "Can't create folder in different bucket";

  Class<?> groups()[] default {};

  Class<? extends Payload>[] payload() default {};
}
