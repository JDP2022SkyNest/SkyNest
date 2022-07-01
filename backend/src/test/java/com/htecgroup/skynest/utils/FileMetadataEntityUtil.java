package com.htecgroup.skynest.utils;

import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.entity.FileMetadataEntity;
import com.htecgroup.skynest.model.entity.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileMetadataEntityUtil {

  public static FileMetadataEntity getFileMetadataEntity() {

    UserEntity user = UserEntityUtil.getVerified();
    BucketEntity bucket = BucketEntityUtil.getPrivateBucket();

    FileMetadataEntity fileMetadata =
        new FileMetadataEntity(null, bucket, "application/x-sh", 35, "62bc7643bc47ca6d0b782b9b");

    fileMetadata.setId(UUID.fromString("508a2754-7557-4651-9a46-7650c061d8f3"));
    fileMetadata.setCreatedOn(BasicUtil.currentDateTime);
    fileMetadata.setModifiedOn(BasicUtil.currentDateTime);
    fileMetadata.setDeletedOn(null);
    fileMetadata.setCreatedBy(user);
    fileMetadata.setName("ping-ports.sh");

    return fileMetadata;
  }
}
