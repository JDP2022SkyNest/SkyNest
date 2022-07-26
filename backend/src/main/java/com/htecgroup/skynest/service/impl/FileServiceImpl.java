package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.annotation.actions.RecordAction;
import com.htecgroup.skynest.exception.UserNotFoundException;
import com.htecgroup.skynest.exception.buckets.BucketAccessDeniedException;
import com.htecgroup.skynest.exception.buckets.BucketAlreadyDeletedException;
import com.htecgroup.skynest.exception.buckets.BucketNotFoundException;
import com.htecgroup.skynest.exception.buckets.BucketsTooFullException;
import com.htecgroup.skynest.exception.file.*;
import com.htecgroup.skynest.exception.folder.FolderAlreadyDeletedException;
import com.htecgroup.skynest.exception.folder.FolderNotFoundException;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.entity.*;
import com.htecgroup.skynest.model.request.FileInfoEditRequest;
import com.htecgroup.skynest.model.response.FileDownloadResponse;
import com.htecgroup.skynest.model.response.FileResponse;
import com.htecgroup.skynest.model.response.TagResponse;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.repository.FileMetadataRepository;
import com.htecgroup.skynest.repository.FolderRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileServiceImpl implements FileService {

  private final ModelMapper modelMapper;
  private final FileContentService fileContentService;
  private final CurrentUserServiceImpl currentUserService;
  private final BucketRepository bucketRepository;
  private final UserRepository userRepository;
  private final FileMetadataRepository fileMetadataRepository;
  private final ActionService actionService;
  private final PermissionService permissionService;
  private final TagService tagService;
  private final FolderRepository folderRepository;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public FileResponse uploadFileToBucket(MultipartFile multipartFile, UUID bucketId) {

    FileMetadataEntity emptyFileMetadata =
        initFileMetadataWithBucket(
            multipartFile.getOriginalFilename(),
            multipartFile.getSize(),
            multipartFile.getContentType(),
            bucketId);

    if (!emptyFileMetadata.getBucket().getIsPublic())
      permissionService.currentUserHasPermissionForBucket(
          emptyFileMetadata.getBucket().getId(), AccessType.EDIT);

    FileMetadataEntity savedFileMetadata = saveContentAndMetadata(emptyFileMetadata, multipartFile);
    permissionService.grantOwnerForObject(savedFileMetadata);
    actionService.recordAction(Collections.singleton(savedFileMetadata), ActionType.CREATE);

    return modelMapper.map(savedFileMetadata, FileResponse.class);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public FileResponse uploadFileToFolder(MultipartFile multipartFile, UUID folderId) {

    FileMetadataEntity emptyFileMetadata =
        initFileMetadataWithFolder(
            multipartFile.getOriginalFilename(),
            multipartFile.getSize(),
            multipartFile.getContentType(),
            folderId);

    checkFolderNotDeleted(emptyFileMetadata);
    if (!emptyFileMetadata.getBucket().getIsPublic()) {
      permissionService.currentUserHasPermissionForFolder(
          emptyFileMetadata.getParentFolder(), AccessType.EDIT);
    }

    FileMetadataEntity savedFileMetadata = saveContentAndMetadata(emptyFileMetadata, multipartFile);
    permissionService.grantOwnerForObject(savedFileMetadata);
    actionService.recordAction(Collections.singleton(savedFileMetadata), ActionType.CREATE);

    return modelMapper.map(savedFileMetadata, FileResponse.class);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public FileResponse getFileMetadata(UUID fileId) {

    FileMetadataEntity fileMetadataEntity = findFileMetadataById(fileId);
    checkOnlyEmployeesCanAccessCompanyBuckets(fileMetadataEntity);

    if (!fileMetadataEntity.getBucket().getIsPublic())
      permissionService.currentUserHasPermissionForFile(fileMetadataEntity, AccessType.VIEW);

    List<TagResponse> tags = tagService.getTagsForObject(fileId);

    actionService.recordAction(Collections.singleton(fileMetadataEntity), ActionType.VIEW);

    return modelMapper.map(fileMetadataEntity, FileResponse.class).withTags(tags);
  }

  @Override
  public FileDownloadResponse downloadFile(UUID fileId) {

    FileMetadataEntity fileMetadataEntity = findFileMetadataById(fileId);
    checkOnlyEmployeesCanAccessCompanyBuckets(fileMetadataEntity);

    if (!fileMetadataEntity.getBucket().getIsPublic())
      permissionService.currentUserHasPermissionForFile(fileMetadataEntity, AccessType.DOWNLOAD);

    Resource fileContents = fileContentService.findById(fileMetadataEntity.getContentId());
    actionService.recordAction(Collections.singleton(fileMetadataEntity), ActionType.DOWNLOAD);

    return new FileDownloadResponse(
        fileMetadataEntity.getName(), fileMetadataEntity.getType(), fileContents);
  }

  @Override
  public FileResponse editFileInfo(FileInfoEditRequest fileInfoEditRequest, UUID fileId) {

    FileMetadataEntity fileMetadataEntity =
        fileMetadataRepository.findById(fileId).orElseThrow(FileNotFoundException::new);

    if (!fileMetadataEntity.getBucket().getIsPublic())
      permissionService.currentUserHasPermissionForFile(fileMetadataEntity, AccessType.EDIT);

    if (fileMetadataEntity.isDeleted()) {
      throw new FileAlreadyDeletedException();
    }
    fileInfoEditRequest.setName(fileInfoEditRequest.getName().trim());

    modelMapper.map(fileInfoEditRequest, fileMetadataEntity);
    FileMetadataEntity savedFileEntity = fileMetadataRepository.save(fileMetadataEntity);

    actionService.recordAction(Collections.singleton(savedFileEntity), ActionType.EDIT);
    return modelMapper.map(savedFileEntity, FileResponse.class);
  }

  @Override
  public List<FileResponse> getAllRootFiles(UUID bucketId) {
    List<FileMetadataEntity> allFiles =
        fileMetadataRepository.findAllByBucketIdAndParentFolderIsNullOrderByNameAscCreatedOnDesc(
            bucketId);
    return asFileResponseList(allFiles);
  }

  @Override
  public List<FileResponse> getAllFilesWithParent(UUID parentFolderId) {
    List<FileMetadataEntity> allFiles =
        fileMetadataRepository.findAllByParentFolderIdOrderByNameAscCreatedOnDesc(parentFolderId);
    return asFileResponseList(allFiles);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public FileResponse editFileContent(MultipartFile multipartFile, UUID fileId) {

    FileMetadataEntity fileMetadata =
        fileMetadataRepository.findById(fileId).orElseThrow(FileNotFoundException::new);

    String oldFileType = fileMetadata.getType();
    String newFileType = multipartFile.getContentType();
    if (!oldFileType.equals(newFileType)) throw new FileCannotChangeTypeException();

    fileMetadata.setType(multipartFile.getContentType());
    fileMetadata.setSize(multipartFile.getSize());

    checkOnlyCreatorsCanAccessPrivateBuckets(fileMetadata);

    String oldFileContentId = fileMetadata.getContentId();
    FileMetadataEntity savedFileMetadata = saveContentAndMetadata(fileMetadata, multipartFile);
    fileContentService.deleteById(oldFileContentId);
    actionService.recordAction(Collections.singleton(savedFileMetadata), ActionType.EDIT);

    return modelMapper.map(savedFileMetadata, FileResponse.class);
  }

  @Override
  public void moveFileToFolder(UUID fileId, UUID destinationFolderId) {
    FileMetadataEntity fileMetadataEntity = findFileMetadataById(fileId);
    checkIfDeleted(fileMetadataEntity);
    FolderEntity folderEntity =
        folderRepository.findById(destinationFolderId).orElseThrow(FolderNotFoundException::new);
    checkIfFileAlreadyInsideFolder(fileMetadataEntity, folderEntity);
    fileMetadataEntity.moveToFolder(fileMetadataEntity, folderEntity);
    saveMoveFile(fileMetadataEntity);
  }

  @Override
  public void moveFileToRoot(UUID fileId) {
    FileMetadataEntity fileMetadataEntity = findFileMetadataById(fileId);
    checkIfDeleted(fileMetadataEntity);
    checkIfFileIsAlreadyInsideRoot(fileMetadataEntity);
    fileMetadataEntity.moveToRoot(fileMetadataEntity);
    saveMoveFile(fileMetadataEntity);
  }

  private void checkIfDeleted(FileMetadataEntity fileMetadataEntity) {
    if (fileMetadataEntity.isDeleted()) {
      throw new FileAlreadyDeletedException();
    }
  }

  private void checkIfFileAlreadyInsideFolder(
      FileMetadataEntity fileMetadataEntity, FolderEntity folderEntity) {
    if (fileMetadataEntity.getParentFolder() != null
        && fileMetadataEntity.getParentFolder().getId() == folderEntity.getId()) {
      throw new FileAlreadyInsideFolderException();
    }
  }

  private void checkIfFileIsAlreadyInsideRoot(FileMetadataEntity fileMetadataEntity) {
    if (fileMetadataEntity.getParentFolder() == null) {
      throw new FileAlreadyInsideRootException();
    }
  }

  private void saveMoveFile(FileMetadataEntity fileMetadataEntity) {
    fileMetadataRepository.save(fileMetadataEntity);
    actionService.recordAction(Collections.singleton(fileMetadataEntity), ActionType.MOVE);
  }

  private List<FileResponse> asFileResponseList(List<FileMetadataEntity> allFiles) {
    return allFiles.stream()
        .map(folder -> modelMapper.map(folder, FileResponse.class))
        .map(file -> file.withTags(tagService.getTagsForObject(file.getId())))
        .collect(Collectors.toList());
  }

  @Override
  @RecordAction(objectId = "[0].toString()", actionType = ActionType.DELETE)
  public void deleteFile(UUID fileId) {
    FileMetadataEntity fileMetadataEntity = findFileMetadataById(fileId);
    if (fileMetadataEntity.isDeleted()) {
      throw new FileAlreadyDeletedException();
    }
    if (!fileMetadataEntity.getBucket().getIsPublic()) {
      permissionService.currentUserHasPermissionForFile(fileMetadataEntity, AccessType.EDIT);
    }
    fileMetadataEntity.delete();
    fileMetadataRepository.save(fileMetadataEntity);
  }

  private FileMetadataEntity saveContentAndMetadata(
      FileMetadataEntity fileMetadata, MultipartFile multipartFile) {

    checkOnlyEmployeesCanAccessCompanyBuckets(fileMetadata);
    checkBucketNotDeleted(fileMetadata);
    checkBucketSizeExceedsMax(fileMetadata);

    try {
      InputStream fileContent = multipartFile.getInputStream();

      String fileContentId =
          fileContentService.save(fileMetadata.getName(), fileMetadata.getType(), fileContent);
      fileMetadata.setContentId(fileContentId);

      return fileMetadataRepository.save(fileMetadata);
    } catch (IOException e) {
      log.error(e);
      throw new FileIOException();
    }
  }

  private FileMetadataEntity findFileMetadataById(UUID fileId) {
    return fileMetadataRepository.findById(fileId).orElseThrow(FileNotFoundException::new);
  }

  @Override
  @RecordAction(objectId = "[0].toString()", actionType = ActionType.RESTORE)
  public FileResponse restoreFile(UUID fileId) {
    LoggedUserDto currentUser = currentUserService.getLoggedUser();

    FileMetadataEntity fileMetadataEntity =
        fileMetadataRepository.findById(fileId).orElseThrow(FileNotFoundException::new);

    if (!fileMetadataEntity.isDeleted()) {
      throw new FileAlreadyRestoredException();
    }

    if (!fileMetadataEntity.getBucket().getIsPublic()) {
      permissionService.currentUserHasPermissionForFile(fileMetadataEntity, AccessType.EDIT);
    }

    if (fileMetadataEntity.someParentIsDeleted()) {
      throw new FileParentIsDeletedException();
    }

    log.info(
        "User {} ({}) is attempting to restore file {} ({})",
        currentUser.getUsername(),
        currentUser.getUuid(),
        fileMetadataEntity.getName(),
        fileMetadataEntity.getId());

    fileMetadataEntity.restore();
    FileMetadataEntity savedFileMetadataEntity = fileMetadataRepository.save(fileMetadataEntity);
    return modelMapper.map(savedFileMetadataEntity, FileResponse.class);
  }

  private FileMetadataEntity initFileMetadata(
      String name, long size, String type, BucketEntity bucket, FolderEntity parentFolder) {

    UserEntity currentUserEntity =
        userRepository
            .findById(currentUserService.getLoggedUser().getUuid())
            .orElseThrow(UserNotFoundException::new);

    FileMetadataEntity fileMetadataEntity = new FileMetadataEntity();

    fileMetadataEntity.setCreatedBy(currentUserEntity);
    fileMetadataEntity.setName(name);

    fileMetadataEntity.setSize(size);
    fileMetadataEntity.setType(type);

    fileMetadataEntity.setBucket(bucket);
    fileMetadataEntity.setParentFolder(parentFolder);

    return fileMetadataEntity;
  }

  private FileMetadataEntity initFileMetadataWithBucket(
      String name, long size, String type, UUID bucketId) {

    FolderEntity parentFolder = null;
    BucketEntity bucket =
        bucketRepository.findById(bucketId).orElseThrow(BucketNotFoundException::new);

    return initFileMetadata(name, size, type, bucket, parentFolder);
  }

  private FileMetadataEntity initFileMetadataWithFolder(
      String name, long size, String type, UUID folderId) {

    FolderEntity parentFolder =
        folderRepository.findById(folderId).orElseThrow(FolderNotFoundException::new);
    BucketEntity bucket = parentFolder.getBucket();

    return initFileMetadata(name, size, type, bucket, parentFolder);
  }

  private void checkOnlyCreatorsCanAccessPrivateBuckets(FileMetadataEntity fileMetadataEntity) {

    BucketEntity bucket = fileMetadataEntity.getBucket();
    UUID bucketCreatorId = bucket.getCreatedBy().getId();
    UUID currentUserId = currentUserService.getLoggedUser().getUuid();

    if (!bucket.getIsPublic() && !currentUserId.equals(bucketCreatorId))
      throw new BucketAccessDeniedException();
  }

  private void checkOnlyEmployeesCanAccessCompanyBuckets(FileMetadataEntity fileMetadataEntity) {

    BucketEntity bucket = fileMetadataEntity.getBucket();

    UUID companyId = bucket.getCompany().getId();

    LoggedUserDto currentUser = currentUserService.getLoggedUser();
    if (currentUser.getCompany() == null) throw new BucketAccessDeniedException();

    UUID currentUserCompanyId = currentUserService.getLoggedUser().getCompany().getId();

    if (bucket.getIsPublic() && !currentUserCompanyId.equals(companyId))
      throw new BucketAccessDeniedException();
  }

  private void checkBucketNotDeleted(FileMetadataEntity fileMetadataEntity) {
    if (fileMetadataEntity.getBucket().isDeleted()) throw new BucketAlreadyDeletedException();
  }

  private void checkFolderNotDeleted(FileMetadataEntity fileMetadataEntity) {
    if (fileMetadataEntity.getParentFolder().isDeleted()) throw new FolderAlreadyDeletedException();
  }

  private void checkBucketSizeExceedsMax(FileMetadataEntity fileMetadataEntity) {

    BucketEntity bucket = fileMetadataEntity.getBucket();

    long maxAllowedSize;
    long currentTotalSize;

    CompanyEntity company = bucket.getCompany();

    UUID companyId = company.getId();
    currentTotalSize = bucketRepository.sumSizeByCompanyId(companyId);

    TierEntity tierEntity = company.getTier();
    TierType tierType = TierType.valueOf(tierEntity.getName().toUpperCase());
    maxAllowedSize = tierType.getMaxSize();

    if (currentTotalSize + fileMetadataEntity.getSize() > maxAllowedSize)
      throw new BucketsTooFullException();
  }
}
