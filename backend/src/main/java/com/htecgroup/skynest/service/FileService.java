package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.request.FileInfoEditRequest;
import com.htecgroup.skynest.model.response.FileDownloadResponse;
import com.htecgroup.skynest.model.response.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileService {

  FileResponse uploadFile(MultipartFile multipartFile, UUID bucketId);

  FileResponse getFileMetadata(UUID fileId);

  FileDownloadResponse downloadFile(UUID fileId);

  FileResponse editFileInfo(FileInfoEditRequest fileInfoEditRequest, UUID fileId);
}
