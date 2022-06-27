package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.annotation.UniqueCompany;
import com.htecgroup.skynest.exception.company.CompanyAlreadyDeletedException;
import com.htecgroup.skynest.exception.company.CompanyNotFoundException;
import com.htecgroup.skynest.exception.tier.NonExistingTierException;
import com.htecgroup.skynest.model.entity.CompanyEntity;
import com.htecgroup.skynest.model.entity.TierEntity;
import com.htecgroup.skynest.model.request.CompanyAddRequest;
import com.htecgroup.skynest.model.response.CompanyResponse;
import com.htecgroup.skynest.repository.CompanyRepository;
import com.htecgroup.skynest.repository.TierRepository;
import com.htecgroup.skynest.service.CompanyService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Validated
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

  private CompanyRepository companyRepository;
  private TierRepository tierRepository;
  private ModelMapper modelMapper;

  @Override
  public CompanyResponse addCompany(@Valid @UniqueCompany CompanyAddRequest companyAddRequest) {

    TierEntity tierEntity =
        tierRepository
            .findByName(companyAddRequest.getTierName())
            .orElseThrow(NonExistingTierException::new);

    CompanyEntity companyEntity = modelMapper.map(companyAddRequest, CompanyEntity.class);
    companyEntity.setTier(tierEntity);
    companyEntity = companyRepository.save(companyEntity);

    return modelMapper.map(companyEntity, CompanyResponse.class);
  }

  @Override
  public CompanyResponse deleteCompany(UUID uuid) {

    CompanyEntity companyEntity =
        companyRepository.findById(uuid).orElseThrow(CompanyNotFoundException::new);

    if (companyEntity.getDeletedOn() != null) {
      throw new CompanyAlreadyDeletedException();
    }

    companyRepository.save(companyEntity.withDeletedOn(LocalDateTime.now()));

    return modelMapper.map(companyEntity, CompanyResponse.class);
  }
}
