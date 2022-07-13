package com.htecgroup.skynest.utils.company;

import com.htecgroup.skynest.model.request.CompanyAddRequest;

public class CompanyAddRequestUtil extends CompanyBasicUtil {

  public static CompanyAddRequest get() {
    return new CompanyAddRequest(pib, name, address, phoneNumber, email, tierName);
  }
}
