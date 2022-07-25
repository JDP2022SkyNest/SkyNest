package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.buckets.BucketAlreadyDeletedException;
import com.htecgroup.skynest.exception.buckets.BucketAlreadyRestoredException;
import com.htecgroup.skynest.exception.buckets.BucketNotFoundException;
import com.htecgroup.skynest.lambda.LambdaType;
import com.htecgroup.skynest.model.dto.BucketDto;
import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.request.BucketCreateRequest;
import com.htecgroup.skynest.model.request.BucketEditRequest;
import com.htecgroup.skynest.model.response.BucketResponse;
import com.htecgroup.skynest.model.response.FileResponse;
import com.htecgroup.skynest.model.response.FolderResponse;
import com.htecgroup.skynest.model.response.StorageContentResponse;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.repository.UserRepository;
import com.htecgroup.skynest.service.*;
import com.htecgroup.skynest.utils.*;
import com.htecgroup.skynest.utils.tag.TagResponseUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BucketServiceImplTest {

  @Mock private BucketRepository bucketRepository;
  @Mock private CurrentUserService currentUserService;
  @Mock private UserRepository userRepository;
  @Mock private ActionService actionService;
  @Mock private PermissionService permissionService;
  @Spy private ModelMapper modelMapper;
  @Spy @InjectMocks private BucketServiceImpl bucketService;
  @Mock private FolderService folderService;
  @Mock private FileService fileService;
  @Mock private TagService tagService;

  @Captor private ArgumentCaptor<BucketEntity> captorBucketEntity;

  @Test
  void listAllBuckets() {
    List<BucketEntity> bucketEntityList =
        Collections.singletonList(BucketEntityUtil.getPrivateBucket());
    when(bucketRepository.findAllByOrderByNameAscCreatedOnDesc()).thenReturn(bucketEntityList);
    when(tagService.getTagsForObject(any())).thenReturn(Collections.singletonList(TagResponseUtil.get()));

    List<BucketEntity> expectedResponse = new ArrayList<>(bucketEntityList);

    List<BucketResponse> actualResponse = bucketService.listAllBuckets();

    Assertions.assertEquals(expectedResponse.size(), actualResponse.size());
    this.assertBucketEntityAndBucketResponse(expectedResponse.get(0), actualResponse.get(0));
    verify(bucketRepository, times(1)).findAllByOrderByNameAscCreatedOnDesc();
    verify(tagService,times(1)).getTagsForObject(bucketEntityList.get(0).getId());
  }

  @Test
  void getBucket() {
    BucketEntity expectedBucketEntity = BucketEntityUtil.getPrivateBucket();
    when(bucketRepository.findById(any())).thenReturn(Optional.of(expectedBucketEntity));
    when(tagService.getTagsForObject(any())).thenReturn(Collections.singletonList(TagResponseUtil.get()));

    BucketResponse actualBucketResponse =
        bucketService.getBucketDetails(expectedBucketEntity.getId());

    this.assertBucketEntityAndBucketResponse(expectedBucketEntity, actualBucketResponse);
    verify(bucketRepository, times(1)).findById(any());
    verify(tagService,times(1)).getTagsForObject(expectedBucketEntity.getId());
  }

  @Test
  void getBucketContent() {

    when(bucketRepository.existsById(any())).thenReturn(true);
    List<FolderResponse> expectedFolderResponseList =
        new ArrayList<>(Collections.singleton(FolderResponseUtil.getRootFolder()));
    when(folderService.getAllRootFolders(any())).thenReturn(expectedFolderResponseList);

    List<FileResponse> expectedFileResponseList =
        new ArrayList<>(Collections.singleton(FileResponseUtil.getRootFile()));
    when(fileService.getAllRootFiles(any())).thenReturn(expectedFileResponseList);

    UUID bucketId = FolderEntityUtil.getFolderWithoutParent().getBucket().getId();
    StorageContentResponse expectedStorageContentResponse =
        new StorageContentResponse(
            bucketId, expectedFolderResponseList, expectedFileResponseList, null);

    StorageContentResponse actualStorageContentResponse = bucketService.getBucketContent(bucketId);

    Assertions.assertEquals(expectedStorageContentResponse, actualStorageContentResponse);
    verify(bucketRepository, times(1)).existsById(bucketId);
    verify(folderService, times(1)).getAllRootFolders(bucketId);
    verify(fileService, times(1)).getAllRootFiles(bucketId);
  }

  @Test
  void when_getBucketContent_ShouldThrowBucketNotFound() {
    String expectedErrorMessage = BucketNotFoundException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            BucketNotFoundException.class, () -> bucketService.getBucketContent(UUID.randomUUID()));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void createBucket() {
    BucketEntity expectedBucketEntity = BucketEntityUtil.getPrivateBucket();
    when(currentUserService.getLoggedUser()).thenReturn(LoggedUserDtoUtil.getLoggedWorkerUser());
    when(userRepository.getById(any())).thenReturn(UserEntityUtil.getVerified());
    when(bucketRepository.save(any())).thenReturn(expectedBucketEntity);

    BucketCreateRequest bucketCreateRequest = BucketCreateRequestUtil.get();
    BucketResponse actualBucketResponse = bucketService.createBucket(bucketCreateRequest);

    this.assertBucketEntityAndBucketResponse(expectedBucketEntity, actualBucketResponse);
    verify(currentUserService, times(1)).getLoggedUser();
    verify(userRepository, times(1)).getById(any());
    verify(bucketRepository, times(1)).save(any());
  }

  @Test
  void editBucket() {
    BucketEntity expectedBucketEntity = BucketEntityUtil.getPrivateBucket();
    when(bucketRepository.findById(any())).thenReturn(Optional.of(expectedBucketEntity));
    when(bucketRepository.save(any())).thenReturn(expectedBucketEntity);

    BucketEditRequest bucketEditRequest = BucketEditRequestUtil.get();
    BucketResponse actualBucketResponse =
        bucketService.editBucket(bucketEditRequest, expectedBucketEntity.getId());

    this.assertBucketEntityAndBucketResponse(expectedBucketEntity, actualBucketResponse);
    verify(bucketRepository, times(1)).findById(any());
    verify(bucketRepository, times(1)).save(any());
  }

  @Test
  void when_editBucket_ShouldThrowBucketAlreadyDeleted() {
    BucketEntity bucketEntity = BucketEntityUtil.getDeletedBucket();
    BucketEditRequest bucketEditRequest = BucketEditRequestUtil.get();
    doReturn(bucketEntity).when(bucketService).findBucketEntityById(any());
    String expectedErrorMessage = BucketAlreadyDeletedException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            BucketAlreadyDeletedException.class,
            () -> bucketService.editBucket(bucketEditRequest, UUID.randomUUID()));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
    verify(bucketService, times(1)).findBucketEntityById(any());
  }

  @Test
  void when_AlreadyDeletedBucket_deletedBucket_ShouldThrowBucketAlreadyDeleted() {
    BucketDto bucketDto = BucketDtoUtil.getCurrentUsersDeletedBucket();
    doReturn(bucketDto).when(bucketService).findBucketById(any());
    String expectedErrorMessage = BucketAlreadyDeletedException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            BucketAlreadyDeletedException.class,
            () -> bucketService.deleteBucket(UUID.randomUUID()));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
    verify(bucketService, times(1)).findBucketById(any());
  }

  @Test
  void when_deleteBucket_ShouldDeleteBucket() {
    BucketDto bucketDto = BucketDtoUtil.getCurrentUsersNotDeletedBucket();
    doReturn(bucketDto).when(bucketService).findBucketById(any());

    bucketService.deleteBucket(UUID.randomUUID());
    Mockito.verify(bucketRepository).save(captorBucketEntity.capture());

    BucketEntity bucketEntity = captorBucketEntity.getValue();
    Assertions.assertTrue(bucketEntity.isDeleted());
    verify(bucketService, times(1)).findBucketById(any());
  }

  private void assertBucketEntityAndBucketResponse(
      BucketEntity expectedBucketEntity, BucketResponse actualBucketResponse) {
    Assertions.assertEquals(expectedBucketEntity.getId(), actualBucketResponse.getBucketId());
    Assertions.assertEquals(
        expectedBucketEntity.getCreatedBy().getId(), actualBucketResponse.getCreatedById());
    Assertions.assertEquals(expectedBucketEntity.getName(), actualBucketResponse.getName());
    Assertions.assertEquals(
        expectedBucketEntity.getCompany().getId(), actualBucketResponse.getCompanyId());
    Assertions.assertEquals(
        expectedBucketEntity.getDescription(), actualBucketResponse.getDescription());
    Assertions.assertEquals(expectedBucketEntity.getSize(), actualBucketResponse.getSize());
    Assertions.assertEquals(expectedBucketEntity.getIsPublic(), actualBucketResponse.getIsPublic());
  }

  @Test
  void when_AlreadyRestoredBucket_restoreBucket_ShouldThrowBucketAlreadyRestored() {
    BucketDto bucketDto = BucketDtoUtil.getCurrentUsersNotDeletedBucket();
    doReturn(bucketDto).when(bucketService).findBucketById(any());
    String expectedErrorMessage = BucketAlreadyRestoredException.MESSAGE;
    Exception thrownException =
        Assertions.assertThrows(
            BucketAlreadyRestoredException.class,
            () -> bucketService.restoreBucket(UUID.randomUUID()));
    Assertions.assertEquals(expectedErrorMessage, thrownException.getMessage());
  }

  @Test
  void when_restoreBucket_ShouldRestoreBucket() {
    BucketDto bucketDto = BucketDtoUtil.getCurrentUsersDeletedBucket();
    doReturn(bucketDto).when(bucketService).findBucketById(any());

    bucketService.restoreBucket(UUID.randomUUID());
    Mockito.verify(bucketRepository).save(captorBucketEntity.capture());

    BucketEntity bucketEntity = captorBucketEntity.getValue();
    Assertions.assertFalse(bucketEntity.isDeleted());
  }

  @Test
  void when_activateLambda_ShouldSaveEntityWithActivatedLambdas() {
    BucketEntity bucketEntity = BucketEntityUtil.getPrivateBucket();
    LambdaType lambdaType = LambdaType.UPLOAD_FILE_TO_EXTERNAL_SERVICE_LAMBDA;
    doReturn(bucketEntity).when(bucketService).findBucketEntityById(any());

    bucketService.activateLambda(UUID.randomUUID(), lambdaType);
    Mockito.verify(bucketRepository).save(captorBucketEntity.capture());

    BucketEntity bucketWithActivatedLambda = captorBucketEntity.getValue();
    Assertions.assertTrue(bucketWithActivatedLambda.getLambdaTypes().contains(lambdaType));
  }

  @Test
  void when_deactivateLambda_ShouldSaveEntityWithDeactivatedLambda() {
    BucketEntity bucketEntity = BucketEntityUtil.getPrivateBucketWithLambdas();
    LambdaType lambdaType = LambdaType.UPLOAD_FILE_TO_EXTERNAL_SERVICE_LAMBDA;
    doReturn(bucketEntity).when(bucketService).findBucketEntityById(any());

    bucketService.deactivateLambda(UUID.randomUUID(), lambdaType);
    Mockito.verify(bucketRepository).save(captorBucketEntity.capture());

    BucketEntity bucketWithActivatedLambda = captorBucketEntity.getValue();
    Assertions.assertFalse(bucketWithActivatedLambda.getLambdaTypes().contains(lambdaType));
  }

  @Test
  void when_getActiveLambdas_ShouldReturnAllActiveLambdas() {
    BucketEntity bucketWithLambda = BucketEntityUtil.getPrivateBucketWithLambdas();
    doReturn(bucketWithLambda).when(bucketService).findBucketEntityById(any());
    UUID bucketId = UUID.randomUUID();
    List<LambdaType> returnedLambdas = bucketService.getActiveLambdas(bucketId);

    Assertions.assertTrue(
        returnedLambdas.contains(LambdaType.UPLOAD_FILE_TO_EXTERNAL_SERVICE_LAMBDA));
    Assertions.assertEquals(1, returnedLambdas.size());
  }
}
