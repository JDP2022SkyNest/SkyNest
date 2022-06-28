package com.htecgroup.skynest.annotation;

import com.htecgroup.skynest.annotation.validator.EmailAndPhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Constraint(validatedBy = EmailAndPhoneNumberValidator.class)
public @interface UniqueEmailAndPhoneNumber {

  String message() default "Email or phone number already in use";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
