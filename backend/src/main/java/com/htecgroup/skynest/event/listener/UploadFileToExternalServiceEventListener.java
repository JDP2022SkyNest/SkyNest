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

  // TODO: Inject factory for creating lambdas. Create lambda for event and execute it.
  // Publishing events is going to be done in the service on the action that we want our lambda to
  // be executed on.

  private BaseLambdaFactory baseLambdaFactory;

  @Override
  public void onApplicationEvent(UploadFileToExternalServiceEvent event) {
    Lambda lambda =
        baseLambdaFactory.createLambda(LambdaType.UPLOAD_FILE_TO_EXTERNAL_SERVICE_LAMBDA);
    lambda.doLambdaFunction(event);
  }
}
