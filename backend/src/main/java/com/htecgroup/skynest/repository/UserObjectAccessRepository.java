package com.htecgroup.skynest.repository;

import com.htecgroup.skynest.model.entity.UserObjectAccessEntity;
import com.htecgroup.skynest.model.entity.UserObjectAccessKey;
import org.springframework.data.repository.CrudRepository;

public interface UserObjectAccessRepository
    extends CrudRepository<UserObjectAccessEntity, UserObjectAccessKey> {}
