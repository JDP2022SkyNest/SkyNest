package com.htecgroup.skynest.event.listener;

import com.htecgroup.skynest.event.UploadFileToExternalServiceEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UploadFileToExternalServiceEventListener
    implements ApplicationListener<UploadFileToExternalServiceEvent> {

  // TODO: Inject factory for creating lambdas. Create lambda for event and execute it.
  // Publishing events is going to be done in the service on the action that we want our lambda to
  // be executed on.
  @Override
  public void onApplicationEvent(UploadFileToExternalServiceEvent event) {}
}
