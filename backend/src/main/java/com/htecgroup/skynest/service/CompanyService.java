package com.htecgroup.skynest.service;

import com.htecgroup.skynest.annotation.UniqueEmailAndPhoneNumber;
import com.htecgroup.skynest.model.request.CompanyAddRequest;
import com.htecgroup.skynest.model.response.CompanyResponse;

import javax.validation.Valid;

public interface CompanyService {

  CompanyResponse addCompany(@Valid @UniqueEmailAndPhoneNumber CompanyAddRequest companyAddRequest);
}