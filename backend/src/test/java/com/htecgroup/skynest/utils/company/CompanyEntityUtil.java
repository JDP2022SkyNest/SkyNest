package com.htecgroup.skynest.utils.company;

import com.htecgroup.skynest.model.entity.CompanyEntity;

public class CompanyEntityUtil extends CompanyBasicUtil {

  public static CompanyEntity getCompanyEntity() {
    return new CompanyEntity(
        id, createdOn, modifiedOn, deletedOn, pib, name, address, phoneNumber, email, tierEntity);
  }
}
