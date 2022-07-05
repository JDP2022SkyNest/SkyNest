package com.htecgroup.skynest.repository;

import com.htecgroup.skynest.model.entity.FolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FolderRepository extends JpaRepository<FolderEntity, UUID> {
  FolderEntity findFolderById(UUID uuid);
}