package com.htecgroup.skynest.repository;

import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.model.entity.UserObjectAccessEntity;
import com.htecgroup.skynest.model.entity.UserObjectAccessKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserObjectAccessRepository
    extends CrudRepository<UserObjectAccessEntity, UserObjectAccessKey> {
  List<UserObjectAccessEntity> findAllByObjectId(UUID objectId);

  Optional<UserObjectAccessEntity> findByObjectId(UUID objectId);

  Optional<UserObjectAccessEntity> findByObjectIdAndGrantedToEmail(UUID bucketId, String email);

  Optional<UserObjectAccessEntity> findByObjectIdAndGrantedTo(UUID bucketId, UserEntity user);
}
