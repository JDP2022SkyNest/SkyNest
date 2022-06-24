package com.htecgroup.skynest.repository;

import com.htecgroup.skynest.model.entity.TierEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TierRepository extends CrudRepository<TierEntity, UUID> {}
