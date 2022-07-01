package com.htecgroup.skynest.utils.company;

import com.htecgroup.skynest.model.dto.CompanyDto;

public class CompanyDtoUtil extends CompanyBasicUtil {

  public static CompanyDto getCompanyDto() {
    return new CompanyDto(
        id, createdOn, modifiedOn, deletedOn, pib, name, address, phoneNumber, email, tierName);
  }
}
