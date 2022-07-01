package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.annotation.UniqueCompany;
import com.htecgroup.skynest.exception.company.CompanyNotFoundException;
import com.htecgroup.skynest.exception.company.UserNotInAnyCompanyException;
import com.htecgroup.skynest.exception.tier.NonExistingTierException;
import com.htecgroup.skynest.model.dto.CompanyDto;
import com.htecgroup.skynest.model.entity.CompanyEntity;
import com.htecgroup.skynest.model.entity.TierEntity;
import com.htecgroup.skynest.model.request.CompanyAddRequest;
import com.htecgroup.skynest.model.request.CompanyEditRequest;
import com.htecgroup.skynest.model.response.CompanyResponse;
import com.htecgroup.skynest.repository.CompanyRepository;
import com.htecgroup.skynest.repository.TierRepository;
import com.htecgroup.skynest.service.CompanyService;
import com.htecgroup.skynest.service.CurrentUserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.UUID;

@Service
@Validated
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

  private CompanyRepository companyRepository;
  private CurrentUserService currentUserService;
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
  public CompanyResponse editCompany(CompanyEditRequest companyEditRequest) {

    CompanyEntity companyEntity =
        currentUserService
            .getCompanyEntityFromLoggedUser()
            .orElseThrow(UserNotInAnyCompanyException::new);

    companyEditRequest.setName(companyEditRequest.getName().trim());
    companyEditRequest.setAddress(companyEditRequest.getAddress().trim());
    modelMapper.map(companyEditRequest, companyEntity);
    companyRepository.save(companyEntity);

    return modelMapper.map(companyEntity, CompanyResponse.class);
  }

  @Override
  public CompanyDto findById(UUID companyId) {
    CompanyEntity companyEntity =
        companyRepository.findById(companyId).orElseThrow(CompanyNotFoundException::new);
    return modelMapper.map(companyEntity, CompanyDto.class);
  }
}
