package com.htecgroup.skynest.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UrlUtil {
  public static final String USERS_CONTROLLER_URL = "/users";
  public static final String COMPANY_CONTROLLER_URL = "/companies";
  public static final String PUBLIC_CONTROLLER_URL = "/public";
  public static final String LOG_IN_URL = "/login";
  public static final String REGISTER_URL = "/register";
  public static final String CONFIRM_EMAIL_URL = "/confirm";
  public static final String RESEND_EMAIL_URL = "/resend-email";
  public static final String PASSWORD_RESET_URL = "/password-reset";

  public static final String REFRESH_TOKEN = "/token/refresh";

  public static String EMAIL_VERIFICATION_URL;
  public static String PASSWORD_RESET_FRONTEND_URL;

  public static String getEmailVerificationLink(String token) {
    return EMAIL_VERIFICATION_URL + token;
  }

  public static String getPasswordResetLink(String token) {
    return PASSWORD_RESET_FRONTEND_URL + token;
  }

  @Value("${backend.app.emailConfirmationLink}")
  private void setEmailVerificationUrl(String emailVerificationUrl) {
    EMAIL_VERIFICATION_URL = emailVerificationUrl;
  }

  @Value("${backend.app.passwordResetLink}")
  private void setPasswordResetUrl(String passwordResetUrl) {
    PASSWORD_RESET_FRONTEND_URL = passwordResetUrl;
  }
}
