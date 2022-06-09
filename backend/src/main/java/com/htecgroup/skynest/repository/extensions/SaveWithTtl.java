package com.htecgroup.skynest.repository.extensions;

public interface SaveWithTtl<T> {
  <S extends T> S save(S entity, int ttl);
}
