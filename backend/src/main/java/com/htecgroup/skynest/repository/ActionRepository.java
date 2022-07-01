package com.htecgroup.skynest.repository;

import com.htecgroup.skynest.model.entity.ActionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ActionRepository extends CrudRepository<ActionEntity, UUID> {

  List<ActionEntity> findAllByUserIdOrderByPerformedOnDesc(UUID userId);

  @Query(
      "SELECT a FROM action a WHERE (SELECT o FROM object o WHERE id = ?1) MEMBER OF objects ORDER BY performedOn DESC")
  List<ActionEntity> findAllByObjectIdOrderByPerformedOnDesc(UUID objectId);
}
