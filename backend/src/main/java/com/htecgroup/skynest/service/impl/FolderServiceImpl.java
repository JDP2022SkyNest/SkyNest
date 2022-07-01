package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.folder.FolderNotFoundException;
import com.htecgroup.skynest.model.dto.FolderDto;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.FolderEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.model.request.FolderCreateRequest;
import com.htecgroup.skynest.model.response.FolderResponse;
import com.htecgroup.skynest.repository.FolderRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.BucketService;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.service.FolderService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FolderServiceImpl implements FolderService {

  private ModelMapper modelMapper;
  private FolderRepository folderRepository;
  private CurrentUserService currentUserService;

  private UserRepository userRepository;

  private BucketService bucketService;

  @Override
  public FolderResponse createFolder(FolderCreateRequest folderCreateRequest) {
    FolderEntity folderEntity = modelMapper.map(folderCreateRequest, FolderEntity.class);

    LoggedUserDto loggedUserDto = currentUserService.getLoggedUser();
    UserEntity currentUser = userRepository.getById(loggedUserDto.getUuid());
    BucketEntity bucketEntity =
        modelMapper.map(
            bucketService.findBucketByName(folderCreateRequest.getBucketName()),
            BucketEntity.class);

    if (folderCreateRequest.getParentFolderName() == null
        || folderCreateRequest.getParentFolderName().isEmpty()) {
      folderEntity.setParentFolder(null);
    } else {
      FolderEntity parentFolderEntity =
          modelMapper.map(
              findFolderByName(folderCreateRequest.getParentFolderName()), FolderEntity.class);
      folderEntity.setParentFolder(parentFolderEntity);
    }

    folderEntity.setBucket(bucketEntity);
    folderEntity.setCreatedBy(currentUser);

    folderEntity = folderRepository.save(folderEntity);
    return modelMapper.map(folderEntity, FolderResponse.class);
  }

  @Override
  public FolderDto findFolderByName(String name) {
    FolderEntity folderEntity =
        folderRepository.findFolderByName(name).orElseThrow(FolderNotFoundException::new);
    return modelMapper.map(folderEntity, FolderDto.class);
  }
}
