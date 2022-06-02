package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.model.enitity.RoleEntity;
import com.htecgroup.skynest.repository.RoleRepository;
import com.htecgroup.skynest.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    @Override
    public RoleEntity findByName(String name) {
       return roleRepository
                .findByName(name)
                .orElseThrow(
                        () ->
                                new UserException(
                                        "Role " + RoleEntity.ROLE_WORKER + " not found.",
                                        HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
