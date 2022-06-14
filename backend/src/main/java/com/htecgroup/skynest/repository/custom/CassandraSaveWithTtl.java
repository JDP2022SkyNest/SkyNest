package com.htecgroup.skynest.repository.custom;

public interface CassandraSaveWithTtl<T> {
  <S extends T> S save(S entity, int timeToLiveInSeconds);
}
