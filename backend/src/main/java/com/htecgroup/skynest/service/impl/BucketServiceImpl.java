package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.annotation.CanBucketBeEdited;
import com.htecgroup.skynest.exception.buckets.BucketNotFoundException;
import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.request.BucketEditRequest;
import com.htecgroup.skynest.model.response.BucketResponse;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.service.BucketService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.UUID;

@Service
@Validated
@AllArgsConstructor
public class BucketServiceImpl implements BucketService {

  private BucketRepository bucketRepository;
  private ModelMapper modelMapper;

  @Override
  public BucketResponse getBucket(UUID uuid) {
    BucketEntity bucketEntity =
        bucketRepository.findById(uuid).orElseThrow(BucketNotFoundException::new);
    BucketResponse bucketResponse = modelMapper.map(bucketEntity, BucketResponse.class);
    return bucketResponse;
  }

  @Override
  public BucketResponse editBucket(
      BucketEditRequest bucketEditRequest, @Valid @CanBucketBeEdited UUID uuid) {
    BucketEntity bucketEntity =
        bucketRepository.findById(uuid).orElseThrow(BucketNotFoundException::new);
    bucketEditRequest.setName(bucketEditRequest.getName().trim());
    bucketEditRequest.setDescription(bucketEditRequest.getDescription().trim());
    modelMapper.map(bucketEditRequest, bucketEntity);
    bucketRepository.save(bucketEntity);
    return modelMapper.map(bucketEntity, BucketResponse.class);
  }
}
