package com.htecgroup.skynest.annotation.validator;

import com.htecgroup.skynest.annotation.UniqueEmailAndPhoneNumber;
import com.htecgroup.skynest.exception.register.EmailAlreadyInUseException;
import com.htecgroup.skynest.model.request.CompanyAddRequest;
import com.htecgroup.skynest.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
public class EmailAndPhoneNumberValidator
    implements ConstraintValidator<UniqueEmailAndPhoneNumber, CompanyAddRequest> {

  private final CompanyRepository companyRepository;

  @Override
  public boolean isValid(CompanyAddRequest value, ConstraintValidatorContext context) {
    if (companyRepository.existsByEmail(value.getEmail())) {
      throw new EmailAlreadyInUseException();
    }
    if (companyRepository.existsByPhoneNumber(value.getEmail())) {
      throw new EmailAlreadyInUseException();
    }
    return true;
  }
}
