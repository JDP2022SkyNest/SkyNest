package com.htecgroup.skynest.repository;

import com.htecgroup.skynest.model.entity.FolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FolderRepository extends JpaRepository<FolderEntity, UUID> {

  @Query(
      "SELECT f FROM folder f JOIN user_object_access u ON (u.object.id = f.id) WHERE (SELECT t FROM tag t WHERE id = ?1) MEMBER OF f.tags AND u.grantedTo.id = ?2")
  List<FolderEntity> findAllByTagIdWhereUserCanView(UUID tagId, UUID userId);

  List<FolderEntity> findAllByBucketIdAndParentFolderIsNullOrderByNameAscCreatedOnDesc(
      UUID bucketId);

  List<FolderEntity> findAllByParentFolderIdOrderByNameAscCreatedOnDesc(UUID parentFolderId);

  FolderEntity findFolderById(UUID uuid);
}
