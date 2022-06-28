package com.htecgroup.skynest.repository;

import com.htecgroup.skynest.model.entity.ActionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ActionRepository extends CrudRepository<ActionEntity, UUID> {}
