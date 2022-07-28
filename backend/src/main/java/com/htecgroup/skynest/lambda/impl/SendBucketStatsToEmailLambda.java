package com.htecgroup.skynest.lambda.impl;

import com.htecgroup.skynest.event.SendBucketStatsEvent;
import com.htecgroup.skynest.lambda.Lambda;
import com.htecgroup.skynest.lambda.LambdaType;
import com.htecgroup.skynest.model.email.Email;
import com.htecgroup.skynest.model.entity.ActionType;
import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.model.response.FileResponse;
import com.htecgroup.skynest.model.response.FileStatsEmailResponse;
import com.htecgroup.skynest.service.ActionService;
import com.htecgroup.skynest.service.CurrentUserService;
import com.htecgroup.skynest.service.EmailService;
import com.htecgroup.skynest.service.FileService;
import com.htecgroup.skynest.util.EmailUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Log4j2
@AllArgsConstructor
public class SendBucketStatsToEmailLambda implements Lambda<SendBucketStatsEvent, Boolean> {

  private final FileService fileService;
  private final EmailService emailService;
  private final ActionService actionService;
  private final CurrentUserService currentUserService;

  @Override
  public Boolean doLambdaFunction(SendBucketStatsEvent event) {
    BucketEntity bucket = event.getBucket();
    List<FileResponse> fileResponses = fileService.getFilesInBucket(bucket.getId());
    List<FileStatsEmailResponse> fileStatsEmailResponses =
        getFileStatsEmailResponses(fileResponses);
    Email email =
        EmailUtil.createStatsEmailForBucket(
            fileStatsEmailResponses, bucket.getName(), currentUserService.getLoggedUser());
    emailService.send(email);
    return true;
  }

  @Override
  public LambdaType getLambdaType() {
    return LambdaType.SEND_BUCKET_STATS_TO_EMAIL_LAMBDA;
  }

  private List<FileStatsEmailResponse> getFileStatsEmailResponses(
      List<FileResponse> fileResponses) {
    return fileResponses.stream()
        .map(
            fileResponse -> {
              Integer downloads =
                  actionService
                      .getActionsWithTypeForObject(ActionType.DOWNLOAD, fileResponse.getId(), true)
                      .size();
              Integer views =
                  actionService
                      .getActionsWithTypeForObject(ActionType.VIEW, fileResponse.getId(), true)
                      .size();
              Integer edits =
                  actionService
                      .getActionsWithTypeForObject(ActionType.EDIT, fileResponse.getId(), true)
                      .size();
              Integer moves =
                  actionService
                      .getActionsWithTypeForObject(ActionType.MOVE, fileResponse.getId(), true)
                      .size();
              Double size = Double.parseDouble(fileResponse.getSize()) / 1000000;
              return new FileStatsEmailResponse(
                  fileResponse.getName(), downloads, edits, views, moves, size.toString());
            })
        .collect(Collectors.toList());
  }
}
