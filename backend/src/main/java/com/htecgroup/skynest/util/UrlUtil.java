package com.htecgroup.skynest.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UrlUtil {
  public static final String USERS_CONTROLLER_URL = "/users";
  public static final String PUBLIC_CONTROLLER_URL = "/public";
  public static final String LOG_IN_URL = "/login";
  public static final String REGISTER_URL = "/register";
  public static final String CONFIRM_EMAIL_URL = "/confirm";
  public static final String RESEND_EMAIL_URL = "/resend-email";
  public static final String PASSWORD_RESET_URL = "/password-reset";
}
