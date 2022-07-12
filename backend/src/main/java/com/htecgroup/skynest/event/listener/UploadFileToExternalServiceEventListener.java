package com.htecgroup.skynest.event.listener;

import com.htecgroup.skynest.event.UploadFileToExternalServiceEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UploadFileToExternalServiceEventListener
    implements ApplicationListener<UploadFileToExternalServiceEvent> {

  // TODO: Inject factory for creating lambdas. Create lambda for event and execute it.

  @Override
  public void onApplicationEvent(UploadFileToExternalServiceEvent event) {}
}
