package com.htecgroup.skynest.repository;

import com.htecgroup.skynest.model.entity.CompanyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CompanyRepository extends CrudRepository<CompanyEntity, UUID> {}
