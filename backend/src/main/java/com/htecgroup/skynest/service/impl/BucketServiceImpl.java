package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.buckets.BucketAlreadyDeletedException;
import com.htecgroup.skynest.exception.buckets.BucketNotFoundException;
import com.htecgroup.skynest.model.dto.BucketDto;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.CompanyEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.model.request.BucketCreateRequest;
import com.htecgroup.skynest.model.response.BucketResponse;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.BucketService;
import com.htecgroup.skynest.service.CurrentUserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BucketServiceImpl implements BucketService {

  private BucketRepository bucketRepository;
  private ModelMapper modelMapper;
  private CurrentUserService currentUserService;
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
  }

  @Override
  public BucketResponse getBucket(UUID uuid) {
    BucketEntity bucketEntity =
        bucketRepository.findById(uuid).orElseThrow(BucketNotFoundException::new);
    BucketResponse bucketResponse = modelMapper.map(bucketEntity, BucketResponse.class);
    return bucketResponse;
  }

  @Override
  public BucketDto findBucketByName(String name) {
    BucketEntity bucketEntity =
        bucketRepository.findBucketByName(name).orElseThrow(BucketNotFoundException::new);
    return modelMapper.map(bucketEntity, BucketDto.class);
  }

  @Override
  public List<BucketResponse> listAllBuckets() {
    List<BucketEntity> entityList = (List<BucketEntity>) bucketRepository.findAll();
    return entityList.stream()
        .map(e -> modelMapper.map(e, BucketResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  public BucketDto findBucketById(UUID uuid) {
    BucketEntity bucketEntity =
        bucketRepository.findById(uuid).orElseThrow(BucketNotFoundException::new);
    return modelMapper.map(bucketEntity, BucketDto.class);
  }

  @Override
  public void deleteBucket(UUID uuid) {
    BucketDto bucketDto = findBucketById(uuid);
    if (bucketDto.getDeletedOn() != null) {
      throw new BucketAlreadyDeletedException();
    }
    BucketDto deletedBucketDto = bucketDto.deleteBucket();
    bucketRepository.save(modelMapper.map(deletedBucketDto, BucketEntity.class));
  }
}
