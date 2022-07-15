package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.request.FileInfoEditRequest;
import com.htecgroup.skynest.model.response.FileDownloadResponse;
import com.htecgroup.skynest.model.response.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface FileService {

  FileResponse uploadFileToBucket(MultipartFile multipartFile, UUID bucketId);

  FileResponse uploadFileToFolder(MultipartFile multipartFile, UUID folderId);

  FileResponse getFileMetadata(UUID fileId);

  FileDownloadResponse downloadFile(UUID fileId);

  FileResponse editFileInfo(FileInfoEditRequest fileInfoEditRequest, UUID fileId);

  List<FileResponse> getAllRootFiles(UUID bucketId);

  List<FileResponse> getAllFilesWithParent(UUID parentFolderId);

  void moveFileToFolder(UUID fileId, UUID destinationFolderId);

  void moveFileToRoot(UUID fileId);

  FileResponse editFileContent(MultipartFile multipartFile, UUID fileId);

  void deleteFile(UUID fileId);
}
