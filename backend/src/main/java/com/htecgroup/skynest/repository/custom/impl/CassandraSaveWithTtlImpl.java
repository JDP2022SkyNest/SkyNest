package com.htecgroup.skynest.repository.custom.impl;

import com.htecgroup.skynest.repository.custom.CassandraSaveWithTtl;
import lombok.AllArgsConstructor;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.InsertOptions;
import org.springframework.util.Assert;

@AllArgsConstructor
public class CassandraSaveWithTtlImpl<T> implements CassandraSaveWithTtl<T> {

  private CassandraOperations operations;

  @Override
  public <S extends T> S save(S entity, int timeToLiveInSeconds) {

    Assert.notNull(entity, "Entity must not be null");
    Assert.isTrue(timeToLiveInSeconds >= 0, "Time to live must be greater than zero");

    InsertOptions insertOptions =
        InsertOptions.builder().ttl(timeToLiveInSeconds).ifNotExists(true).build();
    operations.insert(entity, insertOptions);
    return entity;
  }
}
