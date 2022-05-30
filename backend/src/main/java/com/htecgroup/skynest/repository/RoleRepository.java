package com.htecgroup.skynest.repository;

import com.htecgroup.skynest.model.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, UUID> {
  Optional<RoleEntity> findByName(String name);
}
