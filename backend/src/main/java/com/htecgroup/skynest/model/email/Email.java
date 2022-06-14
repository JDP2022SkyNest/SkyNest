package com.htecgroup.skynest.model.email;

import com.htecgroup.skynest.util.EmailType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class Email {
  String to;
  EmailType emailType;
  Map<String, String> args;
  Boolean html;
}
