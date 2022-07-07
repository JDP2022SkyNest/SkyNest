package com.htecgroup.skynest.annotation.validator;

import com.htecgroup.skynest.annotation.ParentFolderIsInTheSameBucket;
import com.htecgroup.skynest.exception.folder.ParentFolderDoesntExistException;
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
    if (folderCreateRequest.getParentFolderId() == null) {
      return true;
    }
    BucketEntity bucketEntity =
        modelMapper.map(
            bucketService.findBucketById(folderCreateRequest.getBucketId()), BucketEntity.class);

    FolderEntity parentFolderEntity =
        folderRepository
            .findById(folderCreateRequest.getParentFolderId())
            .orElseThrow(ParentFolderDoesntExistException::new);

    if (bucketEntity.getId() != parentFolderEntity.getBucket().getId()) {
      return false;
    }
    return true;
  }
}
