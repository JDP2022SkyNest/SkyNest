package com.htecgroup.skynest.model.email;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class Email {
  String to;
  String subject;
  String template;
  Map<String, String> args;
  Boolean html;
}
