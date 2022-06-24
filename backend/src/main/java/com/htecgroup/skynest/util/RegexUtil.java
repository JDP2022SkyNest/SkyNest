package com.htecgroup.skynest.util;

public class RegexUtil {

  public static final String PASSWORD_FORMAT_REGEX =
      "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d!@#&()–\\[{}\\]:\\-;',?|/*%~$_^+=<>\\s]{8,50}";

  public static final String PHONE_NUMBER_FORMAT_REGEX = "[\\d]+";

  public static final String EMAIL_FORMAT_REGEX =
      "[a-zA-Z0-9_+&*-]{1,64}(?:\\.[a-zA-Z0-9_+&*-]+){0,64}@(?:[a-zA-Z0-9-]+\\.){1,255}[a-zA-Z]{2,7}";
}
