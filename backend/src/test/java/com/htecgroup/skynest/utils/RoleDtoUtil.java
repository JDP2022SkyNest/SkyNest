package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.dto.RoleDto;

public class RoleDtoUtil extends BasicUtil {

  public static RoleDto getAdminRole() {
    return roleAdminDto;
  }

  public static RoleDto getManagerRole() {
    return roleManagerDto;
  }

  public static RoleDto getWorkerRole() {
    return roleWorkerDto;
  }
}
