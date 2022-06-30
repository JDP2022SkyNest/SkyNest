package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.model.response.FileDownloadResponse;
import com.htecgroup.skynest.model.response.FileResponse;
import com.htecgroup.skynest.service.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/files")
@AllArgsConstructor
@Log4j2
@Tag(name = "File API", description = "File operations")
public class FileController {

  private final FileService fileService;

  @PostMapping("/bucket/{uuid}")
  public ResponseEntity<FileResponse> uploadFileToBucket(
      @PathVariable(name = "uuid") UUID bucketId, @RequestParam("file") MultipartFile file) {

    FileResponse fileResponse = fileService.uploadFile(file, bucketId);

    ResponseEntity<FileResponse> responseEntity = new ResponseEntity<>(fileResponse, HttpStatus.OK);
    return responseEntity;
  }

  @GetMapping("/{uuid}")
  public ResponseEntity<Resource> downloadFile(@PathVariable(name = "uuid") UUID fileId) {

    FileDownloadResponse fileDownloadResponse = fileService.downloadFile(fileId);

    ResponseEntity<Resource> responseEntity =
        ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(fileDownloadResponse.getType()))
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + fileDownloadResponse.getName() + "\"")
            .body(fileDownloadResponse.getFileContent());

    return responseEntity;
  }

  @GetMapping("/{uuid}/metadata")
  public ResponseEntity<FileResponse> getFileDetails(@PathVariable(name = "uuid") UUID fileId) {

    FileResponse fileResponse = fileService.getFileMetadata(fileId);
    ResponseEntity<FileResponse> responseEntity = new ResponseEntity<>(fileResponse, HttpStatus.OK);

    return responseEntity;
  }
}
