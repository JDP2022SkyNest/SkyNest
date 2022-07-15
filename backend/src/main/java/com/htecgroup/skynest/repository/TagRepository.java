package com.htecgroup.skynest.repository;

import com.htecgroup.skynest.model.entity.TagEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TagRepository extends CrudRepository<TagEntity, UUID> {}
