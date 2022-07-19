package com.htecgroup.skynest.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UploadFileToExternalServiceEvent extends ApplicationEvent {

  private final MultipartFile fileToUpload;
  private final String bucketName;

  public UploadFileToExternalServiceEvent(
      Object source, MultipartFile fileToUpload, String bucketId) {
    super(source);
    this.fileToUpload = fileToUpload;
    this.bucketName = bucketId;
  }
}
