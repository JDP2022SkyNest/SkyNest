package com.htecgroup.skynest.repository.extensions.impl;

import com.htecgroup.skynest.repository.extensions.SaveWithTtl;
import lombok.AllArgsConstructor;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.InsertOptions;

@AllArgsConstructor
public class SaveWithTtlImpl<T> implements SaveWithTtl<T> {
  private CassandraOperations operations;

  @Override
  public <S extends T> S save(S entity, int ttl) {
    InsertOptions insertOptions = InsertOptions.builder().ttl(ttl).ifNotExists(true).build();
    operations.insert(entity, insertOptions);
    return entity;
  }
}
