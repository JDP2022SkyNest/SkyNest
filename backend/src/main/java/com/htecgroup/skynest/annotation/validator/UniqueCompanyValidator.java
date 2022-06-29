package com.htecgroup.skynest.annotation.validator;

import com.htecgroup.skynest.annotation.UniqueCompany;
import com.htecgroup.skynest.exception.company.PibAlreadyInUseException;
import com.htecgroup.skynest.exception.register.EmailAlreadyInUseException;
import com.htecgroup.skynest.exception.register.PhoneNumberAlreadyInUseException;
import com.htecgroup.skynest.model.request.CompanyAddRequest;
import com.htecgroup.skynest.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
public class UniqueCompanyValidator
    implements ConstraintValidator<UniqueCompany, CompanyAddRequest> {

  private final CompanyRepository companyRepository;

  @Override
  public boolean isValid(CompanyAddRequest value, ConstraintValidatorContext context) {

    if (companyRepository.existsByPib(value.getPib())) {
      throw new PibAlreadyInUseException();
    }
    if (companyRepository.existsByEmail(value.getEmail())) {
      throw new EmailAlreadyInUseException();
    }
    if (companyRepository.existsByPhoneNumber(value.getPhoneNumber())) {
      throw new PhoneNumberAlreadyInUseException();
    }
    return true;
  }
}
