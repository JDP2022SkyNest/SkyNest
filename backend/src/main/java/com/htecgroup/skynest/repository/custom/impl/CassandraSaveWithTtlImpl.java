package com.htecgroup.skynest.repository.custom.impl;

import com.htecgroup.skynest.repository.custom.CassandraSaveWithTtl;
import lombok.AllArgsConstructor;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.InsertOptions;

@AllArgsConstructor
public class CassandraSaveWithTtlImpl<T> implements CassandraSaveWithTtl<T> {

  private CassandraOperations operations;

  @Override
  public <S extends T> S save(S entity, int timeToLiveInSeconds) {
    InsertOptions insertOptions =
        InsertOptions.builder().ttl(timeToLiveInSeconds).ifNotExists(true).build();
    operations.insert(entity, insertOptions);
    return entity;
  }
}
