package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.CompanyEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.model.request.BucketCreateRequest;
import com.htecgroup.skynest.model.response.BucketResponse;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.exception.buckets.BucketNotFoundException;
import com.htecgroup.skynest.service.BucketService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class BucketServiceImpl implements BucketService {

  private BucketRepository bucketRepository;
  private ModelMapper modelMapper;
  private CurrentUserServiceImpl currentUserService;
  private UserRepository userRepository;

  @Override
  public BucketResponse createBucket(BucketCreateRequest bucketCreateRequest) {

    BucketEntity bucketEntity = modelMapper.map(bucketCreateRequest, BucketEntity.class);

    LoggedUserDto loggedUserDto = currentUserService.getLoggedUser();
    UserEntity currentUser = userRepository.getById(loggedUserDto.getUuid());
    CompanyEntity currentUserCompany = currentUser.getCompany();

    bucketEntity.setName(bucketEntity.getName().trim());
    bucketEntity.setDescription(bucketEntity.getDescription().trim());

    bucketEntity.setCreatedBy(currentUser);
    bucketEntity.setCompany(currentUserCompany);
    bucketEntity.setIsPublic(false);

    bucketEntity = bucketRepository.save(bucketEntity);
    return modelMapper.map(bucketEntity, BucketResponse.class);

  @Override
  public BucketResponse getBucket(UUID uuid) {
    BucketEntity bucketEntity =
        bucketRepository.findById(uuid).orElseThrow(BucketNotFoundException::new);
    BucketResponse bucketResponse = modelMapper.map(bucketEntity, BucketResponse.class);
    return bucketResponse;
  }
}
