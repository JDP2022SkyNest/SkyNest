package com.htecgroup.skynest.repository;

import com.htecgroup.skynest.model.entity.CompanyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends CrudRepository<CompanyEntity, UUID> {
  boolean existsByEmail(String email);

  boolean existsByPhoneNumber(String phoneNumber);

  boolean existsByPib(String pib);

  Optional<CompanyEntity> findByName(String name);
}
