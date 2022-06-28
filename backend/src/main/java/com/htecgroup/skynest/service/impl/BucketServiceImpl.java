package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.service.BucketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BucketServiceImpl implements BucketService {

  private BucketRepository bucketRepository;

  @Override
  public void deleteBucket(UUID uuid) {
    BucketEntity bucketEntity = bucketRepository.findById(uuid).orElseThrow();
    bucketEntity.setDeletedOn(LocalDateTime.now());
  }
}
