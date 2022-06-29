package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.buckets.BucketNotFoundException;
import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.response.BucketResponse;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.service.BucketService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BucketServiceImpl implements BucketService {

  private BucketRepository bucketsRepository;
  private ModelMapper modelMapper;

  @Override
  public BucketResponse getBucket(UUID uuid) {
    BucketEntity bucketEntity =
        bucketsRepository.findById(uuid).orElseThrow(BucketNotFoundException::new);
    BucketResponse bucketResponse = modelMapper.map(bucketEntity, BucketResponse.class);
    return bucketResponse;
  }

  @Override
  public List<BucketResponse> listAllBuckets() {
    List<BucketEntity> entityList = (List<BucketEntity>) bucketsRepository.findAll();
    return entityList.stream()
        .map(e -> modelMapper.map(e, BucketResponse.class))
        .collect(Collectors.toList());
  }
}
