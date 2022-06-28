package com.htecgroup.skynest.repository;

import com.htecgroup.skynest.model.entity.ActionTypeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ActionTypeRepository extends CrudRepository<ActionTypeEntity, UUID> {
  Optional<ActionTypeEntity> findByName(String name);
}
