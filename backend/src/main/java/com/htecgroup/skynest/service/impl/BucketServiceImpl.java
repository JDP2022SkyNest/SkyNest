package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.buckets.BucketAlreadyDeletedException;
import com.htecgroup.skynest.exception.buckets.BucketNotFoundException;
import com.htecgroup.skynest.model.dto.BucketDto;
import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.response.BucketResponse;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.service.BucketService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
  public BucketDto findBucketById(UUID uuid) {
    BucketEntity bucketEntity =
        bucketsRepository.findById(uuid).orElseThrow(BucketNotFoundException::new);
    return modelMapper.map(bucketEntity, BucketDto.class);
  }

  @Override
  public void deleteBucket(UUID uuid) {
    BucketDto bucketDto = findBucketById(uuid);
    if (bucketDto.getDeletedOn() != null) {
      throw new BucketAlreadyDeletedException();
    }
    BucketDto deletedBucketDto = bucketDto.deleteBucket();
    bucketsRepository.save(modelMapper.map(deletedBucketDto, BucketEntity.class));
  }
}
