package com.htecgroup.skynest.utils.company;

import com.htecgroup.skynest.model.entity.CompanyEntity;

import java.util.UUID;

public class CompanyEntityUtil extends CompanyBasicUtil {

  public static CompanyEntity get() {
    return new CompanyEntity(
        id, createdOn, modifiedOn, deletedOn, pib, name, address, phoneNumber, email, tierEntity);
  }
  public static CompanyEntity getOther() {
    return new CompanyEntity(
        UUID.fromString("195cb7b2-115e-48aa-a774-ff2f5194c715"),
        createdOn,
        modifiedOn,
        deletedOn,
        pib,
        name,
        address,
        phoneNumber,
        email,
        tierEntity);
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
