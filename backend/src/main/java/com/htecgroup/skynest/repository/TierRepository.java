package com.htecgroup.skynest.repository;

import com.htecgroup.skynest.model.entity.TierEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface TierRepository extends CrudRepository<TierEntity, UUID> {
  Optional<TierEntity> findByName(String name);
}
