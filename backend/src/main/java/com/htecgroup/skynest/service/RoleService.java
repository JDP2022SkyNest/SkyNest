package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.enitity.RoleEntity;

public interface RoleService {
    RoleEntity findByName(String name);
}
