package com.htecgroup.skynest.repository;

import com.htecgroup.skynest.model.entity.ObjectEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ObjectRepository extends CrudRepository<ObjectEntity, UUID> {}
