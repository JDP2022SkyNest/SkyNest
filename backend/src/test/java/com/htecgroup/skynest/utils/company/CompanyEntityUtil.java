package com.htecgroup.skynest.utils.company;

import com.htecgroup.skynest.model.entity.CompanyEntity;

public class CompanyEntityUtil extends CompanyBasicUtil {

  public static CompanyEntity get() {
    return new CompanyEntity(
        id, createdOn, modifiedOn, deletedOn, pib, name, address, phoneNumber, email, tierEntity);
  }

  public static CompanyEntity getEdited() {
    return new CompanyEntity(
        id,
        createdOn,
        modifiedOn,
        deletedOn,
        pib,
        editedName,
        editedAddress,
        editedPhoneNumber,
        email,
        tierEntity);
  }
}
