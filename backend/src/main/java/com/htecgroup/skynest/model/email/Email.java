package com.htecgroup.skynest.model.email;

import com.htecgroup.skynest.model.response.FileStatsEmailResponse;
import com.htecgroup.skynest.util.EmailType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class Email {
  String to;
  EmailType emailType;
  Map<String, String> args;
  List<FileStatsEmailResponse> fileStatsEmailResponses;
  Boolean html;
}
