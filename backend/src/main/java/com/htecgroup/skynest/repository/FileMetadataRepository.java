package com.htecgroup.skynest.repository;

import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.FileMetadataEntity;
import com.htecgroup.skynest.model.entity.FolderEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface FileMetadataRepository extends CrudRepository<FileMetadataEntity, UUID> {
  boolean existsByNameAndBucketAndParentFolder(
      String name, BucketEntity bucket, FolderEntity parentFolder);

  List<FileMetadataEntity> findAllByBucketIdAndParentFolderIsNullOrderByNameAscCreatedOn(
      UUID bucketId);

  List<FileMetadataEntity> findAllByParentFolderIdOrderByNameAscCreatedOn(UUID parentFolderId);
}
