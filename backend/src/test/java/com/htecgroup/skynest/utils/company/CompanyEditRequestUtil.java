package com.htecgroup.skynest.utils.company;

import com.htecgroup.skynest.model.request.CompanyEditRequest;

public class CompanyEditRequestUtil extends CompanyBasicUtil {

  public static CompanyEditRequest get() {
    return new CompanyEditRequest(editedName, editedAddress, editedPhoneNumber);
  }
}
