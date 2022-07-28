package com.htecgroup.skynest.event.listener;

import com.htecgroup.skynest.event.SendBucketStatsEvent;
import com.htecgroup.skynest.lambda.BaseLambdaFactory;
import com.htecgroup.skynest.lambda.Lambda;
import com.htecgroup.skynest.lambda.LambdaType;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SendBucketStatsEventListener implements ApplicationListener<SendBucketStatsEvent> {

  private BaseLambdaFactory baseLambdaFactory;

  @Override
  public void onApplicationEvent(SendBucketStatsEvent event) {
    Lambda lambda = baseLambdaFactory.createLambda(LambdaType.SEND_BUCKET_STATS_TO_EMAIL_LAMBDA);
    lambda.doLambdaFunction(event);
  }
}
