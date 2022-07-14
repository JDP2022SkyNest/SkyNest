package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.entity.AccessType;
import com.htecgroup.skynest.model.request.PermissionGrantRequest;
import com.htecgroup.skynest.model.response.PermissionResponse;

import java.util.List;
import java.util.UUID;

public interface PermissionService {

  PermissionResponse grantPermissionForBucket(PermissionGrantRequest permissionGrantRequest);

  void currentUserHasPermissionForBucket(UUID bucketId, AccessType minimumAccessType);

  List<PermissionResponse> getAllBucketPermission(UUID bucketId);
}
