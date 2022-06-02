package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.dto.RoleDto;

public interface RoleService {
  RoleDto findByName(String name);
}
