package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.model.dto.RoleDto;
import com.htecgroup.skynest.model.entity.RoleEntity;
import com.htecgroup.skynest.repository.RoleRepository;
import com.htecgroup.skynest.service.RoleService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

  private RoleRepository roleRepository;
  private ModelMapper modelMapper;

  @Override
  public RoleDto findByName(String name) {
    RoleEntity roleEntity =
        roleRepository
            .findByName(name)
            .orElseThrow(
                () ->
                    new UserException(
                        String.format("Role %s not found.", name),
                        HttpStatus.INTERNAL_SERVER_ERROR));
    return modelMapper.map(roleEntity, RoleDto.class);
  }
}
