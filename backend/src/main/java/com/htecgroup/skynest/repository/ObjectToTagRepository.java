package com.htecgroup.skynest.repository;

import com.htecgroup.skynest.model.entity.ObjectToTagEntity;
import com.htecgroup.skynest.model.entity.ObjectToTagKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ObjectToTagRepository extends CrudRepository<ObjectToTagEntity, ObjectToTagKey> {
  List<ObjectToTagEntity> findAllByIdObjectId(UUID objectId);
}
