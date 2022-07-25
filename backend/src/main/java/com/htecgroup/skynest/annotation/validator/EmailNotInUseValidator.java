package com.htecgroup.skynest.annotation.validator;

import com.htecgroup.skynest.annotation.EmailNotInUse;
import com.htecgroup.skynest.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
public class EmailNotInUseValidator implements ConstraintValidator<EmailNotInUse, String> {

  private final UserRepository userRepository;

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return !userRepository.existsByEmail(value);
  }
}
