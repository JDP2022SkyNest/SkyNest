package com.htecgroup.skynest.repository;

import com.htecgroup.skynest.model.entity.BucketEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BucketRepository extends CrudRepository<BucketEntity, UUID> {
  Optional<BucketEntity> findBucketByName(String name);

  @Query("SELECT SUM(b.size) FROM bucket b WHERE b.company.id = ?1")
  Long sumSizeByCompanyId(UUID companyId);

  @Query("SELECT SUM(b.size) FROM bucket b WHERE b.company IS NULL AND b.createdBy.id = ?1")
  Long sumSizeByUserId(UUID userId);

  @Query(
      "SELECT b FROM bucket b JOIN user_object_access u ON (u.object.id = b.id) WHERE (SELECT t FROM tag t WHERE id = ?1) MEMBER OF b.tags AND u.grantedTo.id = ?2")
  List<BucketEntity> findAllByTagIdWhereUserCanView(UUID tagId, UUID userId);

  List<BucketEntity> findAllByOrderByNameAscCreatedOnDesc();
}
