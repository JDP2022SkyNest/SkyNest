package com.htecgroup.SkyNest.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

  private static final DateTimeFormatter BASIC_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public static String currentTimeFormatted() {
    return LocalDateTime.now().format(BASIC_FORMATTER);
  }
}
