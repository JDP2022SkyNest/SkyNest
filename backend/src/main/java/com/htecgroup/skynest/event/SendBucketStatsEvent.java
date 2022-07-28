package com.htecgroup.skynest.event;

import com.htecgroup.skynest.model.entity.BucketEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SendBucketStatsEvent extends ApplicationEvent {

  private final BucketEntity bucket;

  public SendBucketStatsEvent(Object source, BucketEntity bucket) {
    super(source);
    this.bucket = bucket;
  }
}
