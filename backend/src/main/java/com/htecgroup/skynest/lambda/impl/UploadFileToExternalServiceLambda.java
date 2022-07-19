package com.htecgroup.skynest.lambda.impl;

import com.dropbox.core.DbxException;
import com.dropbox.core.InvalidAccessTokenException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.Metadata;
import com.htecgroup.skynest.event.UploadFileToExternalServiceEvent;
import com.htecgroup.skynest.exception.lambda.DropboxFailedException;
import com.htecgroup.skynest.exception.lambda.DropboxInvalidAccessTokenException;
import com.htecgroup.skynest.lambda.Lambda;
import com.htecgroup.skynest.lambda.LambdaType;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;


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
      ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes());
      String fileName = event.getFileToUpload().getOriginalFilename();
      Metadata uploadMetaData =
          dropboxClient
              .files()
              .uploadBuilder("/" + event.getBucketName() + "/" + fileName)
              .uploadAndFinish(inputStream);
      log.info("upload meta data to dropbox =====> {}", uploadMetaData.toString());
      inputStream.close();
    } catch (InvalidAccessTokenException e) {
      throw new DropboxInvalidAccessTokenException();
    } catch (IOException | DbxException e) {
      throw new DropboxFailedException();
    }
    return true;
  }

  @Override
  public LambdaType getLambdaType() {
    return LambdaType.UPLOAD_FILE_TO_EXTERNAL_SERVICE_LAMBDA;
  }
}
