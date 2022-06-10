package com.htecgroup.skynest.model.email;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Email {
  String to;
  String subject;
  String template;
  String link;
  Boolean html;
}
