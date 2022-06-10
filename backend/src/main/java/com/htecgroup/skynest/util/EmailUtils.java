package com.htecgroup.skynest.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailUtils {

  public static final String EMAIL_VERIFICATION_SUBJECT = "Password reset for SkyNest";
  public static final String PASSWORD_RESET_SUBJECT = "Password reset for SkyNest";
  public static final String EMAIL_VERIFICATION_TEMPLATE = "emails/verification";
  public static final String PASSWORD_RESET_TEMPLATE = "emails/password-reset";
}
