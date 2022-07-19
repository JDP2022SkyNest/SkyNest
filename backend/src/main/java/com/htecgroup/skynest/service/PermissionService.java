package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.entity.AccessType;
import com.htecgroup.skynest.model.entity.ObjectEntity;
import com.htecgroup.skynest.model.request.PermissionEditRequest;
import com.htecgroup.skynest.model.request.PermissionGrantRequest;
import com.htecgroup.skynest.model.response.PermissionResponse;

import java.util.List;
import java.util.UUID;

public interface PermissionService {

  PermissionResponse grantPermissionForBucket(PermissionGrantRequest permissionGrantRequest);

  void grantOwnerForObject(ObjectEntity object);

  void currentUserHasPermissionForBucket(UUID bucketId, AccessType minimumAccessType);

  List<PermissionResponse> getAllBucketPermission(UUID bucketId);

  PermissionResponse editPermission(PermissionEditRequest permissionEditRequest, UUID bucketId);
}
