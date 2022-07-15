package com.htecgroup.skynest.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UploadFileToExternalServiceEvent extends ApplicationEvent {

  // TODO: Implement event, add parameters for event (LambdaType, File)
  private final MultipartFile fileToUpload;
  private final String filePath;

  public UploadFileToExternalServiceEvent(
      Object source, MultipartFile fileToUpload, String filePath) {
    super(source);
    this.filePath = filePath;
    this.fileToUpload = fileToUpload;
  }
}
