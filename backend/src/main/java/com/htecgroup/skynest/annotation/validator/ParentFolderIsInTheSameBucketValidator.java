package com.htecgroup.skynest.annotation.validator;

import com.htecgroup.skynest.annotation.ParentFolderIsInTheSameBucket;
import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.FolderEntity;
import com.htecgroup.skynest.model.request.FolderCreateRequest;
import com.htecgroup.skynest.repository.FolderRepository;
import com.htecgroup.skynest.service.BucketService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class ParentFolderIsInTheSameBucketValidator
    implements ConstraintValidator<ParentFolderIsInTheSameBucket, FolderCreateRequest> {

  private final BucketService bucketService;
  private final FolderRepository folderRepository;
  private final ModelMapper modelMapper;

  @Override
  public boolean isValid(
      FolderCreateRequest folderCreateRequest,
      ConstraintValidatorContext constraintValidatorContext) {
    BucketEntity bucketEntity =
        modelMapper.map(
            bucketService.findBucketById(folderCreateRequest.getBucketId()), BucketEntity.class);
    FolderEntity parentFolderEntity;
    if (folderCreateRequest.getParentFolderId() != null) {
      parentFolderEntity = folderRepository.findFolderById(folderCreateRequest.getParentFolderId());
    } else {
      return true;
    }

    if (bucketEntity.getId() != parentFolderEntity.getBucket().getId()) {
      return false;
    }
    return true;
  }
}
