package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.service.BucketService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class BucketsServiceImpl implements BucketService {

  private BucketsRepository bucketsRepository;
  private ModelMapper modelMapper;

  @Override
  public List<BucketResponse> listAllBuckets() {
    List<BucketEntity> entityList = bucketsRepository.findAll();
    return entityList.stream()
        .map(e -> modelMapper.map(e, BucketResponse.class))
        .collect(Collectors.toList());
  }
}
