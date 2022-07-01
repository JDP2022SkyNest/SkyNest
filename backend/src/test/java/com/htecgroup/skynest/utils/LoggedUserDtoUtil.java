package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.dto.CompanyDto;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.utils.company.CompanyDtoUtil;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class LoggedUserDtoUtil extends BasicUtil {

  public static LoggedUserDto getLoggedWorkerUser() {
    List<SimpleGrantedAuthority> authorities =
        Collections.singletonList(new SimpleGrantedAuthority(roleWorkerDto.getName()));

    return new LoggedUserDto(
        UUID.randomUUID(),
        name,
        surname,
        null,
        email,
        password,
        positionInCompany,
        true,
        true,
        true,
        true,
        authorities);
  }

  public static LoggedUserDto getLoggedAdminUser() {
    List<SimpleGrantedAuthority> authorities =
        Collections.singletonList(new SimpleGrantedAuthority(roleAdminDto.getName()));

    return new LoggedUserDto(
        UUID.randomUUID(),
        name,
        surname,
        null,
        email,
        password,
        positionInCompany,
        true,
        true,
        true,
        true,
        authorities);
  }

  public static LoggedUserDto getLoggedAdminWithCompany() {
    List<SimpleGrantedAuthority> authorities =
        Collections.singletonList(new SimpleGrantedAuthority(roleAdminDto.getName()));

    CompanyDto companyDto = CompanyDtoUtil.getCompanyDto();

    return new LoggedUserDto(
        UUID.randomUUID(),
        name,
        surname,
        companyDto,
        email,
        password,
        positionInCompany,
        true,
        true,
        true,
        true,
        authorities);
  }
}
