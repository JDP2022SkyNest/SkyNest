package com.htecgroup.skynest.repository;

import com.htecgroup.skynest.model.entity.FileMetadataEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FileRepository extends CrudRepository<FileMetadataEntity, UUID> {}
