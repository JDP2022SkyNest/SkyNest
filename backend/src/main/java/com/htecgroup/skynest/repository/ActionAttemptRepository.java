package com.htecgroup.skynest.repository;

import com.htecgroup.skynest.model.entity.ActionAttemptEntity;
import com.htecgroup.skynest.repository.custom.CassandraSaveWithTtl;
import org.springframework.data.cassandra.repository.MapIdCassandraRepository;

import java.util.UUID;

public interface ActionAttemptRepository
    extends MapIdCassandraRepository<ActionAttemptEntity>,
        CassandraSaveWithTtl<ActionAttemptEntity> {

  long countByUserIdAndAction(UUID userId, String action);
}
