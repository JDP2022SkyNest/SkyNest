package com.htecgroup.skynest.repository;

import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.FileMetadataEntity;
import com.htecgroup.skynest.model.entity.FolderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface FileMetadataRepository extends CrudRepository<FileMetadataEntity, UUID> {

  @Query(
      "SELECT f FROM file f JOIN user_object_access u ON (u.object.id = f.id) WHERE (SELECT t FROM tag t WHERE id = ?1) MEMBER OF f.tags AND u.grantedTo.id = ?2")
  List<FileMetadataEntity> findAllByTagIdWhereUserCanView(UUID tagId, UUID userId);

  boolean existsByNameAndBucketAndParentFolder(
      String name, BucketEntity bucket, FolderEntity parentFolder);

  List<FileMetadataEntity> findAllByBucketIdAndParentFolderIsNullOrderByNameAscCreatedOnDesc(
      UUID bucketId);

  List<FileMetadataEntity> findAllByParentFolderIdOrderByNameAscCreatedOnDesc(UUID parentFolderId);
}
