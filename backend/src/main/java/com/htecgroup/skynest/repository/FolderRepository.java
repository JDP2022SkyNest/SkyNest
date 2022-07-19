package com.htecgroup.skynest.repository;

import com.htecgroup.skynest.model.entity.FolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FolderRepository extends JpaRepository<FolderEntity, UUID> {

  List<FolderEntity> findAllByBucketIdAndParentFolderIsNullOrderByNameAscCreatedOnDesc(
      UUID bucketId);

  List<FolderEntity> findAllByParentFolderIdOrderByNameAscCreatedOnDesc(UUID parentFolderId);

  FolderEntity findFolderById(UUID uuid);
}
