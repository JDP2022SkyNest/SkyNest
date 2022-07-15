package com.htecgroup.skynest.lambda.impl;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.Metadata;
import com.htecgroup.skynest.event.UploadFileToExternalServiceEvent;
import com.htecgroup.skynest.lambda.Lambda;
import com.htecgroup.skynest.lambda.LambdaType;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

// TODO: Instead of String, lambda accepts Event and uploads file to external service, yet to be
// decided.

@Component
@Log4j2
@AllArgsConstructor
public class UploadFileToExternalServiceLambda
    implements Lambda<UploadFileToExternalServiceEvent, Boolean> {

  DbxClientV2 dropboxClient;

  @Override
  public Boolean doLambdaFunction(UploadFileToExternalServiceEvent event) {
    try {
      MultipartFile file = event.getFileToUpload();
      String filePath = event.getFilePath();
      // ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes());
      Metadata uploadMetaData =
          dropboxClient
              .files()
              .uploadBuilder("/bucket/" + event.getFilePath())
              .uploadAndFinish(file.getInputStream());
      log.info("upload meta data =====> {}", uploadMetaData.toString());
      // inputStream.close();
    } catch (IOException | DbxException e) {
      throw new RuntimeException(e);
    }
    return true;
  }

  @Override
  public LambdaType getLambdaType() {
    return LambdaType.UPLOAD_FILE_TO_EXTERNAL_SERVICE_LAMBDA;
  }
}
