package com.htecgroup.skynest.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.stream.Stream;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UrlUtil {

  public static final String SWAGGER_URL = "/v3/api-docs/**";
  public static final String SWAGGER_URL_ALT = "/swagger-ui/**";

  public static final String USERS_CONTROLLER_PATH = "/users";
  public static final String LOG_IN_PATH = "/login";
  public static final String REGISTER_PATH = "/register";
  public static final String CONFIRM_EMAIL_PATH = "/confirm";
  public static final String RESEND_EMAIL_PATH = "/resend-email";
  public static final String PASSWORD_RESET_PATH = "/password-reset";

  public static String BASE_URL_FRONTEND;
  public static final String CONFIRM_PASSWORD_FRONTEND_PATH = "/confirm-password";

  public static final String[] ANY_URLS_WITHOUT_AUTH = {SWAGGER_URL, SWAGGER_URL_ALT};

  public static final String[] GET_URLS_WITHOUT_AUTH = {
    UrlUtil.USERS_CONTROLLER_PATH + CONFIRM_EMAIL_PATH
  };
  public static final String[] POST_URLS_WITHOUT_AUTH = {
    USERS_CONTROLLER_PATH + REGISTER_PATH,
    USERS_CONTROLLER_PATH + RESEND_EMAIL_PATH,
    USERS_CONTROLLER_PATH + PASSWORD_RESET_PATH
  };
  public static final String[] PUT_URLS_WITHOUT_AUTH = {
    USERS_CONTROLLER_PATH + PASSWORD_RESET_PATH
  };

  public static final String[] URLS_WITHOUT_AUTH =
      Stream.of(
              ANY_URLS_WITHOUT_AUTH,
              GET_URLS_WITHOUT_AUTH,
              POST_URLS_WITHOUT_AUTH,
              PUT_URLS_WITHOUT_AUTH,
              new String[] {USERS_CONTROLLER_PATH + LOG_IN_PATH})
          .flatMap(Stream::of)
          .toArray(String[]::new);

  public static String getEmailVerificationLink(String token) {
    return String.format(
        "%s%s%s%s%s",
        getBaseUrlBackend(), USERS_CONTROLLER_PATH, CONFIRM_EMAIL_PATH, "?token=", token);
  }

  public static String getPasswordResetLink(String token) {
    return String.format(
        "%s%s%s%s", BASE_URL_FRONTEND, CONFIRM_PASSWORD_FRONTEND_PATH, "?token=", token);
  }

  public static String getBaseUrlBackend() {
    return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
  }

  @Value("${frontend.base-url}")
  private void setFrontendBaseUrl(String frontendBaseUrl) {
    BASE_URL_FRONTEND = frontendBaseUrl;
  }
}
