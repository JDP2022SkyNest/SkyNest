package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.response.BucketResponse;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.service.BucketService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class BucketsServiceImpl implements BucketService {

  private BucketRepository bucketsRepository;
  private ModelMapper modelMapper;

  @Override
  public List<BucketResponse> listAllBuckets() {
    List<BucketEntity> entityList = (List<BucketEntity>) bucketsRepository.findAll();
    return entityList.stream()
        .map(e -> modelMapper.map(e, BucketResponse.class))
        .collect(Collectors.toList());
  }
}
