package com.htecgroup.skynest.lambda.impl;

import com.htecgroup.skynest.lambda.Lambda;
import com.htecgroup.skynest.lambda.LambdaType;
import org.springframework.stereotype.Component;

// TODO: Instead of String, lambda accepts Event and uploads file to external service, yet to be
// decided.

@Component
public class UploadFileToExternalServiceLambda implements Lambda<String, Boolean> {
  @Override
  public Boolean doLambdaFunction(String objects) {
    return true;
  }

  @Override
  public LambdaType getLambdaType() {
    return LambdaType.UPLOAD_FILE_TO_EXTERNAL_SERVICE_LAMBDA;
  }
}
