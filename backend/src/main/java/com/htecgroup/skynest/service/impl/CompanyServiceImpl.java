package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.company.PibAlreadyInUseException;
import com.htecgroup.skynest.exception.register.EmailAlreadyInUseException;
import com.htecgroup.skynest.exception.register.PhoneNumberAlreadyInUseException;
import com.htecgroup.skynest.exception.tier.NonExistingTierException;
import com.htecgroup.skynest.model.entity.CompanyEntity;
import com.htecgroup.skynest.model.entity.TierEntity;
import com.htecgroup.skynest.model.io.CompanyIO;
import com.htecgroup.skynest.repository.CompanyRepository;
import com.htecgroup.skynest.repository.TierRepository;
import com.htecgroup.skynest.service.CompanyService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

  private CompanyRepository companyRepository;
  private TierRepository tierRepository;
  private ModelMapper modelMapper;

  @Override
  public CompanyIO addCompany(CompanyIO companyIO) {

    if (companyRepository.existsByPib(companyIO.getPib())) {
      throw new PibAlreadyInUseException();
    }
    if (companyRepository.existsByEmail(companyIO.getEmail())) {
      throw new EmailAlreadyInUseException();
    }
    if (companyRepository.existsByPhoneNumber(companyIO.getPhoneNumber())) {
      throw new PhoneNumberAlreadyInUseException();
    }

    TierEntity tierEntity =
        tierRepository
            .findByName(companyIO.getTierName())
            .orElseThrow(NonExistingTierException::new);

    CompanyEntity companyEntity = modelMapper.map(companyIO, CompanyEntity.class);
    companyEntity.setTier(tierEntity);
    companyEntity = companyRepository.save(companyEntity);

    return modelMapper.map(companyEntity, CompanyIO.class);
  }
}
