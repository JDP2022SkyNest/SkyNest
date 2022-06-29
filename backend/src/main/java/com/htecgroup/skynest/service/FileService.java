package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.response.FileResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileService {

  FileResponse uploadFile(MultipartFile multipartFile, UUID bucketId);

  FileResponse getFileMetadata(UUID fileId);

  Resource downloadFile(UUID fileId);
}
