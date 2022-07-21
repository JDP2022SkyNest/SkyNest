package com.htecgroup.skynest.event.listener;

import com.htecgroup.skynest.event.UploadFileToExternalServiceEvent;
import com.htecgroup.skynest.lambda.BaseLambdaFactory;
import com.htecgroup.skynest.lambda.Lambda;
import com.htecgroup.skynest.lambda.LambdaType;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UploadFileToExternalServiceEventListener
    implements ApplicationListener<UploadFileToExternalServiceEvent> {

  private BaseLambdaFactory baseLambdaFactory;

  @Override
  public void onApplicationEvent(UploadFileToExternalServiceEvent event) {
    Lambda lambda =
        baseLambdaFactory.createLambda(LambdaType.UPLOAD_FILE_TO_EXTERNAL_SERVICE_LAMBDA);
    lambda.doLambdaFunction(event);
  }
}
