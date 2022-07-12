package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserNotFoundException;
import com.htecgroup.skynest.exception.buckets.BucketAccessDeniedException;
import com.htecgroup.skynest.exception.buckets.BucketAlreadyDeletedException;
import com.htecgroup.skynest.exception.buckets.BucketNotFoundException;
import com.htecgroup.skynest.exception.buckets.BucketsTooFullException;
import com.htecgroup.skynest.exception.file.FileIOException;
import com.htecgroup.skynest.exception.file.FileNotFoundException;
import com.htecgroup.skynest.exception.folder.FolderAlreadyDeletedException;
import com.htecgroup.skynest.exception.folder.FolderNotFoundException;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.model.entity.*;
import com.htecgroup.skynest.model.response.FileDownloadResponse;
import com.htecgroup.skynest.model.response.FileResponse;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.repository.FileMetadataRepository;
import com.htecgroup.skynest.repository.FolderRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.ActionService;
import com.htecgroup.skynest.service.FileService;
import com.mongodb.MongoException;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
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

  private final GridFsOperations operations;
  private final ModelMapper modelMapper;
  private final CurrentUserServiceImpl currentUserService;
  private final BucketRepository bucketRepository;
  private final UserRepository userRepository;
  private final FileMetadataRepository fileMetadataRepository;
  private final ActionService actionService;
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

    checkOnlyCreatorsCanAccessPrivateBuckets(emptyFileMetadata);
    checkOnlyEmployeesCanAccessCompanyBuckets(emptyFileMetadata);
    checkBucketNotDeleted(emptyFileMetadata);
    checkBucketSizeExceedsMax(emptyFileMetadata);

    return uploadFile(emptyFileMetadata, multipartFile);
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

    checkOnlyCreatorsCanAccessPrivateBuckets(emptyFileMetadata);
    checkOnlyEmployeesCanAccessCompanyBuckets(emptyFileMetadata);
    checkBucketNotDeleted(emptyFileMetadata);
    checkFolderNotDeleted(emptyFileMetadata);
    checkBucketSizeExceedsMax(emptyFileMetadata);

    return uploadFile(emptyFileMetadata, multipartFile);
  }

  private FileResponse uploadFile(
      FileMetadataEntity emptyFileMetadata, MultipartFile multipartFile) {
    try {
      InputStream fileContents = multipartFile.getInputStream();

      FileMetadataEntity savedFileMetadata = storeFileContents(emptyFileMetadata, fileContents);
      actionService.recordAction(Collections.singleton(savedFileMetadata), ActionType.CREATE);

      return modelMapper.map(savedFileMetadata, FileResponse.class);
    } catch (IOException e) {
      log.error(e);
      throw new FileIOException();
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public FileResponse getFileMetadata(UUID fileId) {

    FileMetadataEntity fileMetadataEntity = getFileMetadataEntity(fileId);
    checkOnlyCreatorsCanAccessPrivateBuckets(fileMetadataEntity);
    checkOnlyEmployeesCanAccessCompanyBuckets(fileMetadataEntity);

    actionService.recordAction(Collections.singleton(fileMetadataEntity), ActionType.VIEW);

    return modelMapper.map(fileMetadataEntity, FileResponse.class);
  }

  @Override
  public FileDownloadResponse downloadFile(UUID fileId) {

    FileMetadataEntity fileMetadataEntity = getFileMetadataEntity(fileId);
    checkOnlyCreatorsCanAccessPrivateBuckets(fileMetadataEntity);
    checkOnlyEmployeesCanAccessCompanyBuckets(fileMetadataEntity);

    Resource fileContents = getFileContents(fileMetadataEntity.getContentId());
    actionService.recordAction(Collections.singleton(fileMetadataEntity), ActionType.DOWNLOAD);

    return new FileDownloadResponse(
        fileMetadataEntity.getName(), fileMetadataEntity.getType(), fileContents);
  }

  @Override
  public List<FileResponse> getAllRootFiles(UUID bucketId) {
    List<FileMetadataEntity> allFiles =
        fileMetadataRepository.findAllByBucketIdAndParentFolderIsNull(bucketId);
    return asFileResponseList(allFiles);
  }

  @Override
  public List<FileResponse> getAllFilesWithParent(UUID parentFolderId) {
    List<FileMetadataEntity> allFiles =
        fileMetadataRepository.findAllByParentFolderId(parentFolderId);
    return asFileResponseList(allFiles);
  }

  private List<FileResponse> asFileResponseList(List<FileMetadataEntity> allFiles) {
    return allFiles.stream()
        .map(folder -> modelMapper.map(folder, FileResponse.class))
        .collect(Collectors.toList());
  }

  private FileMetadataEntity initFileMetadata(
      String name, long size, String type, UUID bucketId, UUID folderId) {

    if (bucketId != null && folderId != null) throw new IllegalArgumentException();

    UserEntity currentUserEntity =
        userRepository
            .findById(currentUserService.getLoggedUser().getUuid())
            .orElseThrow(UserNotFoundException::new);

    FileMetadataEntity fileMetadataEntity = new FileMetadataEntity();

    fileMetadataEntity.setCreatedBy(currentUserEntity);
    fileMetadataEntity.setName(name);

    fileMetadataEntity.setSize(size);
    fileMetadataEntity.setType(type);

    BucketEntity bucket;
    FolderEntity parentFolder;
    if (bucketId != null) {
      parentFolder = null;
      bucket = bucketRepository.findById(bucketId).orElseThrow(BucketNotFoundException::new);
    } else {
      parentFolder = folderRepository.findById(folderId).orElseThrow(FolderNotFoundException::new);
      bucket = parentFolder.getBucket();
    }
    fileMetadataEntity.setBucket(bucket);
    fileMetadataEntity.setParentFolder(parentFolder);

    return fileMetadataEntity;
  }

  private FileMetadataEntity initFileMetadataWithBucket(
      String name, long size, String type, UUID bucketId) {
    return initFileMetadata(name, size, type, bucketId, null);
  }

  private FileMetadataEntity initFileMetadataWithFolder(
      String name, long size, String type, UUID folderId) {
    return initFileMetadata(name, size, type, null, folderId);
  }

  private FileMetadataEntity storeFileContents(
      FileMetadataEntity emptyFileMetadata, InputStream fileContents) {

    String fileContentId =
        storeFileContentsToDatabase(
            emptyFileMetadata.getName(), emptyFileMetadata.getType(), fileContents);

    emptyFileMetadata.setContentId(fileContentId);
    return fileMetadataRepository.save(emptyFileMetadata);
  }

  private FileMetadataEntity getFileMetadataEntity(UUID fileId) {
    return fileMetadataRepository.findById(fileId).orElseThrow(FileNotFoundException::new);
  }

  private String storeFileContentsToDatabase(String name, String type, InputStream inputStream) {
    try {
      Object objectId = operations.store(inputStream, name, type);
      return objectId.toString();
    } catch (MongoException e) {
      log.error(e);
      throw new FileIOException();
    }
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

    if (bucket.getCompany() != null) {
      UUID companyId = bucket.getCompany().getId();

      LoggedUserDto currentUser = currentUserService.getLoggedUser();
      if (currentUser.getCompany() == null) throw new BucketAccessDeniedException();

      UUID currentUserCompanyId = currentUserService.getLoggedUser().getCompany().getId();

      if (bucket.getIsPublic() && !currentUserCompanyId.equals(companyId))
        throw new BucketAccessDeniedException();
    }
  }

  private void checkBucketNotDeleted(FileMetadataEntity fileMetadataEntity) {
    if (fileMetadataEntity.getBucket().getDeletedOn() != null)
      throw new BucketAlreadyDeletedException();
  }

  private void checkFolderNotDeleted(FileMetadataEntity fileMetadataEntity) {
    if (fileMetadataEntity.getParentFolder().getDeletedOn() != null)
      throw new FolderAlreadyDeletedException();
  }

  private void checkBucketSizeExceedsMax(FileMetadataEntity fileMetadataEntity) {

    BucketEntity bucket = fileMetadataEntity.getBucket();

    long maxAllowedSize;
    long currentTotalSize;

    if (bucket.getCompany() != null) {

      CompanyEntity company = bucket.getCompany();

      UUID companyId = company.getId();
      currentTotalSize = bucketRepository.sumSizeByCompanyId(companyId);

      TierEntity tierEntity = company.getTier();
      TierType tierType = TierType.valueOf(tierEntity.getName().toUpperCase());
      maxAllowedSize = tierType.getMaxSize();
    } else {

      UUID currentUserId = currentUserService.getLoggedUser().getUuid();
      currentTotalSize = bucketRepository.sumSizeByUserId(currentUserId);

      maxAllowedSize = 1024L * 1024L * 512L; //  512MiB
    }

    if (currentTotalSize + fileMetadataEntity.getSize() > maxAllowedSize)
      throw new BucketsTooFullException();
  }

  private InputStreamResource getFileContents(String objectId) {
    try {
      if (objectId == null || objectId.isEmpty()) throw new FileNotFoundException();

      GridFSFile gridFSFile = operations.findOne(new Query(Criteria.where("_id").is(objectId)));
      if (gridFSFile == null) throw new FileNotFoundException();

      InputStream inputStream = operations.getResource(gridFSFile).getInputStream();
      return new InputStreamResource(inputStream);
    } catch (IOException | MongoException e) {
      log.error(e);
      throw new FileIOException();
    }
  }
}
