package com.htecgroup.skynest.repository;

import com.htecgroup.skynest.model.entity.InvalidJwtEntity;
import com.htecgroup.skynest.repository.custom.CassandraSaveWithTtl;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface InvalidJwtRepository
    extends CassandraRepository<InvalidJwtEntity, String>, CassandraSaveWithTtl<InvalidJwtEntity> {}
