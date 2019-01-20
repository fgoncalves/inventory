package com.fred.inventory.utils;

public class StringUtils {
  /**
   * Check if the provided text is either null or empty
   */
  public static boolean isBlank(CharSequence text) {
    return text == null || text.length() == 0;
  }
}
