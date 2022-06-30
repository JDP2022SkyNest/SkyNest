package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.UserNotFoundException;
import com.htecgroup.skynest.exception.buckets.BucketAccessDeniedException;
import com.htecgroup.skynest.exception.buckets.BucketNotFoundException;
import com.htecgroup.skynest.exception.file.FileAlreadyExistsException;
import com.htecgroup.skynest.exception.file.FileIOException;
import com.htecgroup.skynest.exception.file.FileNotFoundException;
import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.FileMetadataEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
import com.htecgroup.skynest.model.response.FileResponse;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.repository.FileMetadataRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.FileService;
import com.mongodb.MongoException;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

  private final GridFsOperations operations;
  private final BucketRepository bucketRepository;

  private final CurrentUserServiceImpl currentUserService;
  private final UserRepository userRepository;

  private final FileMetadataRepository fileMetadataRepository;

  private final ModelMapper modelMapper;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public FileResponse uploadFile(MultipartFile multipartFile, UUID bucketId) {

    FileMetadataEntity emptyFileMetadata = reserveFileName(multipartFile, bucketId);

    FileMetadataEntity savedFileMetadata = storeFileContents(multipartFile, emptyFileMetadata);

    return modelMapper.map(savedFileMetadata, FileResponse.class);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public FileResponse getFileMetadata(UUID fileId) {

    FileMetadataEntity fileMetadataEntity = getFileMetadataEntity(fileId);
    checkOnlyCreatorsCanAccessPrivateBuckets(fileMetadataEntity);

    return modelMapper.map(fileMetadataEntity, FileResponse.class);
  }

  private FileMetadataEntity getFileMetadataEntity(UUID fileId) {
    return fileMetadataRepository.findById(fileId).orElseThrow(FileNotFoundException::new);
  }

  @Override
  public Resource downloadFile(UUID fileId) {

    FileMetadataEntity fileMetadataEntity = getFileMetadataEntity(fileId);
    checkOnlyCreatorsCanAccessPrivateBuckets(fileMetadataEntity);

    String objectId = fileMetadataEntity.getContentId();
    if (objectId == null || objectId.isEmpty()) throw new FileNotFoundException();

    return getFileContents(objectId);
  }

  private FileMetadataEntity storeFileContents(
      MultipartFile multipartFile, FileMetadataEntity reservedFileMetadataEntity) {
    try {
      String fileContentId = storeFileContentsToDatabase(multipartFile);
      reservedFileMetadataEntity.setContentId(fileContentId);
      return fileMetadataRepository.save(reservedFileMetadataEntity);
    } catch (IOException | MongoException e) {
      fileMetadataRepository.delete(reservedFileMetadataEntity);
      throw new FileIOException();
    }
  }

  private FileMetadataEntity reserveFileName(MultipartFile multipartFile, UUID bucketId) {

    FileMetadataEntity fileMetadataEntity = initFileMetadata(multipartFile, bucketId);

    checkCanCreateFile(fileMetadataEntity);

    return fileMetadataRepository.save(fileMetadataEntity);
  }

  private String storeFileContentsToDatabase(MultipartFile multipartFile) throws IOException {
    Object objectId =
        operations.store(
            multipartFile.getInputStream(),
            multipartFile.getOriginalFilename(),
            multipartFile.getContentType());
    return objectId.toString();
  }

  private void checkCanCreateFile(FileMetadataEntity fileMetadataEntity) {
    checkOnlyCreatorsCanAccessPrivateBuckets(fileMetadataEntity);
    checkFileMustHaveUniqueNameInFolder(fileMetadataEntity);
  }

  private void checkFileMustHaveUniqueNameInFolder(FileMetadataEntity fileMetadataEntity) {
    if (fileMetadataRepository.existsByNameAndBucketAndParentFolder(
        fileMetadataEntity.getName(), fileMetadataEntity.getBucket(), null))
      throw new FileAlreadyExistsException();
  }

  private void checkOnlyCreatorsCanAccessPrivateBuckets(FileMetadataEntity fileMetadataEntity) {

    BucketEntity bucket = fileMetadataEntity.getBucket();
    UUID bucketCreatorId = bucket.getCreatedBy().getId();
    UUID fileCreatorId = fileMetadataEntity.getCreatedBy().getId();

    if (!bucket.getIsPublic() && !bucketCreatorId.equals(fileCreatorId))
      throw new BucketAccessDeniedException();
  }

  private FileMetadataEntity initFileMetadata(MultipartFile multipartFile, UUID bucketId) {

    UserEntity currentUserEntity =
        userRepository
            .findById(currentUserService.getLoggedUser().getUuid())
            .orElseThrow(UserNotFoundException::new);

    BucketEntity bucketEntity =
        bucketRepository.findById(bucketId).orElseThrow(BucketNotFoundException::new);

    FileMetadataEntity fileMetadataEntity = new FileMetadataEntity();

    fileMetadataEntity.setCreatedBy(currentUserEntity);
    fileMetadataEntity.setName(multipartFile.getOriginalFilename());

    fileMetadataEntity.setBucket(bucketEntity);
    fileMetadataEntity.setParentFolder(null);
    fileMetadataEntity.setSize(multipartFile.getSize());
    fileMetadataEntity.setType(multipartFile.getContentType());

    return fileMetadataEntity;
  }

  private InputStreamResource getFileContents(String objectId) {
    GridFSFile gridFSFile = operations.findOne(new Query(Criteria.where("_id").is(objectId)));
    if (gridFSFile == null) throw new FileNotFoundException();
    try {
      InputStream inputStream = operations.getResource(gridFSFile).getInputStream();
      return new InputStreamResource(inputStream);
    } catch (IOException e) {
      throw new FileIOException();
    }
  }
}
