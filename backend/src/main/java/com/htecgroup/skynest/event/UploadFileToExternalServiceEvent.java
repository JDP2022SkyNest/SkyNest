package com.htecgroup.skynest.event;

import org.springframework.context.ApplicationEvent;

public class UploadFileToExternalServiceEvent extends ApplicationEvent {

  // TODO: Implement event, add parameters for event (LambdaType, File)

  public UploadFileToExternalServiceEvent(Object source) {
    super(source);
  }
}
