package com.htecgroup.skynest.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UrlUtil {

  public static final String SWAGGER_URL = "/v3/api-docs/**";
  public static final String SWAGGER_URL_ALT = "/swagger-ui/**";

  public static final String USERS_CONTROLLER_URL = "/users";
  public static final String LOG_IN_URL = "/login";
  public static final String REGISTER_URL = "/register";
  public static final String CONFIRM_EMAIL_URL = "/confirm";
  public static final String RESEND_EMAIL_URL = "/resend-email";
  public static final String PASSWORD_RESET_URL = "/password-reset";

  public static final String[] ANY_URLS_WITHOUT_AUTH = {SWAGGER_URL, SWAGGER_URL_ALT};

  public static final String[] GET_URLS_WITHOUT_AUTH = {
    UrlUtil.USERS_CONTROLLER_URL + CONFIRM_EMAIL_URL
  };
  public static final String[] POST_URLS_WITHOUT_AUTH = {
    USERS_CONTROLLER_URL + REGISTER_URL,
    USERS_CONTROLLER_URL + RESEND_EMAIL_URL,
    USERS_CONTROLLER_URL + PASSWORD_RESET_URL
  };
  public static final String[] PUT_URLS_WITHOUT_AUTH = {USERS_CONTROLLER_URL + PASSWORD_RESET_URL};

  public static final String[] URLS_WITHOUT_AUTH =
      Stream.of(
              ANY_URLS_WITHOUT_AUTH,
              GET_URLS_WITHOUT_AUTH,
              POST_URLS_WITHOUT_AUTH,
              PUT_URLS_WITHOUT_AUTH,
              new String[] {USERS_CONTROLLER_URL + LOG_IN_URL})
          .flatMap(Stream::of)
          .toArray(String[]::new);
}
