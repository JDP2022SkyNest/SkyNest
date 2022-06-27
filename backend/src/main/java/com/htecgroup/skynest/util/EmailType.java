package com.htecgroup.skynest.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailType {
  EMAIL_VERIFICATION("Verify your email for SkyNest", "emails/verification"),
  PASSWORD_CHANGE_NOTIFICATION(
      "Password change for SkyNest", "emails/password-change-notification"),
  PASSWORD_RESET("Password reset for SkyNest", "emails/password-reset");

  private final String subject;
  private final String template;
}
