package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.RoleException;
import com.htecgroup.skynest.model.dto.RoleDto;
import com.htecgroup.skynest.model.entity.RoleEntity;
import com.htecgroup.skynest.repository.RoleRepository;
import com.htecgroup.skynest.service.RoleService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

  private RoleRepository roleRepository;
  private ModelMapper modelMapper;

  @Override
  public RoleDto findByName(String name) {
    RoleEntity roleEntity =
        roleRepository.findByName(name).orElseThrow(() -> RoleException.ROLE_NOT_FOUND);
    return modelMapper.map(roleEntity, RoleDto.class);
  }
}
