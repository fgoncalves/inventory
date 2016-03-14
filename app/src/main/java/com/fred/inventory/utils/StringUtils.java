package com.fred.inventory.utils;

public class StringUtils {
  /**
   * Check if the provided text is either null or empty
   */
  public static boolean isBlank(CharSequence text) {
    return text == null || text.length() == 0;
  }

  /**
   * Check if a given value is blank - see {@link #isBlank(CharSequence)}. If not blank then
   * return said value, otherwise return a default value
   *
   * @param value Value to check if it's blank
   * @param defaultValue Value to return as default
   * @return The value if not blank. The default value otherwise
   */
  public static String valueOrDefault(CharSequence value, CharSequence defaultValue) {
    return isBlank(value) ? defaultValue.toString() : value.toString();
  }

  /**
   * Ensure that the given value is not blank. If it is throw a {@link IllegalArgumentException},
   * if
   * not, simply do nothing.
   *
   * @param value The value to check
   * @param message The message to put into the exception
   * @throws IllegalArgumentException If the passed value is blank
   */
  public static void isPresent(CharSequence value, String message) {
    if (isBlank(value)) throw new IllegalArgumentException(message);
  }
}
