package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.service.BucketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class BucketServiceImpl implements BucketService {

  private BucketRepository bucketRepository;

  @Override
  public void deleteBucket(UUID uuid) {}
}
