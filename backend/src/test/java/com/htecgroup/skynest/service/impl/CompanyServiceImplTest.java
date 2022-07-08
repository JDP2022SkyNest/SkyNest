package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.company.CompanyNotFoundException;
import com.htecgroup.skynest.exception.company.UserNotInAnyCompanyException;
import com.htecgroup.skynest.exception.tier.NonExistingTierException;
import com.htecgroup.skynest.model.dto.CompanyDto;
import com.htecgroup.skynest.model.entity.CompanyEntity;
import com.htecgroup.skynest.model.request.CompanyAddRequest;
import com.htecgroup.skynest.model.request.CompanyEditRequest;
import com.htecgroup.skynest.model.response.CompanyResponse;
import com.htecgroup.skynest.repository.CompanyRepository;
import com.htecgroup.skynest.repository.TierRepository;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.utils.company.CompanyAddRequestUtil;
import com.htecgroup.skynest.utils.company.CompanyEditRequestUtil;
import com.htecgroup.skynest.utils.company.CompanyEntityUtil;
import com.htecgroup.skynest.utils.company.TierEntityUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {
  @Mock private CompanyRepository companyRepository;
  @Mock private CurrentUserService currentUserService;
  @Mock private TierRepository tierRepository;
  @Spy private ModelMapper modelMapper;

  @Spy @InjectMocks private CompanyServiceImpl companyService;

  @Test
  void addCompany() {
    CompanyEntity expectedCompanyEntity = CompanyEntityUtil.get();

    when(tierRepository.findByName(anyString()))
        .thenReturn(Optional.of(TierEntityUtil.getBasicTier()));
    when(companyRepository.save(any())).thenReturn(expectedCompanyEntity);

    CompanyAddRequest companyAddRequest = CompanyAddRequestUtil.get();
    CompanyResponse actualCompanyResponse = companyService.addCompany(companyAddRequest);

    assertCompanyEntityAndCompanyResponse(expectedCompanyEntity, actualCompanyResponse);
  }

  @Test
  void addCompany_NonExistingTier() {
    when(tierRepository.findByName(anyString())).thenReturn(Optional.empty());

    CompanyAddRequest companyAddRequest = CompanyAddRequestUtil.get();

    String expectedErrorMessage = NonExistingTierException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            NonExistingTierException.class, () -> companyService.addCompany(companyAddRequest));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void editCompany() {
    CompanyEntity originalCompanyEntity = CompanyEntityUtil.get();
    CompanyEntity editedCompanyEntity = CompanyEntityUtil.getEdited();
    CompanyEditRequest companyEditRequest = CompanyEditRequestUtil.get();

    when(currentUserService.getCompanyEntityFromLoggedUser())
        .thenReturn(Optional.of(originalCompanyEntity));
    when(companyRepository.save(any())).thenReturn(editedCompanyEntity);

    CompanyResponse actualCompanyResponse = companyService.editCompany(companyEditRequest);

    assertCompanyEntityAndCompanyResponse(editedCompanyEntity, actualCompanyResponse);
  }

  @Test
  void editCompany_UserNotInAnyCompany() {
    CompanyEditRequest companyEditRequest = CompanyEditRequestUtil.get();

    when(currentUserService.getCompanyEntityFromLoggedUser()).thenReturn(Optional.empty());

    CompanyEditRequest companyEditRequest1 = CompanyEditRequestUtil.get();

    String expectedErrorMessage = UserNotInAnyCompanyException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            UserNotInAnyCompanyException.class,
            () -> companyService.editCompany(companyEditRequest));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void findCompanyById() {
    UUID uuid = CompanyEntityUtil.get().getId();

    when(companyRepository.findById(any())).thenReturn(Optional.of(CompanyEntityUtil.get()));

    CompanyDto actualCompanyDto = companyService.findById(uuid);

    assertCompanyEntityAndCompanyDto(CompanyEntityUtil.get(), actualCompanyDto);
  }

  @Test
  void findCompanyById_CompanyNotFound() {
    UUID uuid = CompanyEntityUtil.get().getId();

    when(companyRepository.findById(any())).thenReturn(Optional.empty());

    String expectedErrorMessage = CompanyNotFoundException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            CompanyNotFoundException.class, () -> companyService.findById(uuid));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void getMyCompany() {
    CompanyEntity companyEntity = CompanyEntityUtil.get();

    when(currentUserService.getCompanyEntityFromLoggedUser())
        .thenReturn(Optional.of(companyEntity));

    CompanyResponse actualCompanyResponse = companyService.getMyCompany();

    assertCompanyEntityAndCompanyResponse(companyEntity, actualCompanyResponse);
  }

  @Test
  void getMyCompany_UserNotInAnyCompany() {
    when(currentUserService.getCompanyEntityFromLoggedUser()).thenReturn(Optional.empty());

    String expectedErrorMessage = UserNotInAnyCompanyException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            UserNotInAnyCompanyException.class, () -> companyService.getMyCompany());
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  private void assertCompanyEntityAndCompanyDto(
      CompanyEntity companyEntity, CompanyDto actualCompanyDto) {
    Assertions.assertEquals(companyEntity.getId(), actualCompanyDto.getId());
    Assertions.assertEquals(companyEntity.getPib(), actualCompanyDto.getPib());
    Assertions.assertEquals(companyEntity.getEmail(), actualCompanyDto.getEmail());
    Assertions.assertEquals(companyEntity.getName(), actualCompanyDto.getName());
    Assertions.assertEquals(companyEntity.getAddress(), actualCompanyDto.getAddress());
    Assertions.assertEquals(companyEntity.getPhoneNumber(), actualCompanyDto.getPhoneNumber());
    Assertions.assertEquals(companyEntity.getTier().getName(), actualCompanyDto.getTierName());
    Assertions.assertEquals(companyEntity.getCreatedOn(), actualCompanyDto.getCreatedOn());
    Assertions.assertEquals(companyEntity.getDeletedOn(), actualCompanyDto.getDeletedOn());
    Assertions.assertEquals(companyEntity.getModifiedOn(), actualCompanyDto.getModifiedOn());
  }

  private void assertCompanyEntityAndCompanyResponse(
      CompanyEntity expectedCompanyEntity, CompanyResponse actualCompanyResponse) {
    Assertions.assertEquals(expectedCompanyEntity.getId(), actualCompanyResponse.getId());
    Assertions.assertEquals(expectedCompanyEntity.getPib(), actualCompanyResponse.getPib());
    Assertions.assertEquals(expectedCompanyEntity.getEmail(), actualCompanyResponse.getEmail());
    Assertions.assertEquals(expectedCompanyEntity.getName(), actualCompanyResponse.getName());
    Assertions.assertEquals(expectedCompanyEntity.getAddress(), actualCompanyResponse.getAddress());
    Assertions.assertEquals(
        expectedCompanyEntity.getPhoneNumber(), actualCompanyResponse.getPhoneNumber());
    Assertions.assertEquals(
        expectedCompanyEntity.getTier().getName(), actualCompanyResponse.getTierName());
  }
}
