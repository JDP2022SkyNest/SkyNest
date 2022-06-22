package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.model.dto.RoleDto;
import com.htecgroup.skynest.model.entity.RoleEntity;
import com.htecgroup.skynest.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
  @Mock private RoleRepository roleRepository;
  @Spy private ModelMapper modelMapper;
  @InjectMocks private RoleServiceImpl roleService;

  @Test
  void when_NotExistingName_findByName_ShouldThrowUserException() {
    String name = "ExampleName";
    when(roleRepository.findByName(name)).thenReturn(Optional.empty());
    String expectedErrorMessage = String.format("Role %s not found.", name);
    Exception thrownException =
        Assertions.assertThrows(UserException.class, () -> roleService.findByName(name));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void when_ValidName_findByName_ShouldReturnRoleDto() {
    String name = "role_worker";
    RoleEntity returnedEntity = new RoleEntity(UUID.randomUUID(), name);
    when(roleRepository.findByName(name)).thenReturn(Optional.of(returnedEntity));
    RoleDto roleDto = roleService.findByName(name);
    assertEquals(name, roleDto.getName());
  }
}
