package com.fred.inventory.utils;

/**
 * Backport some of guava's preconditions so we don't flood the app with methods...
 */
public class Preconditions {
  /**
   * Check if the given object is null and if so throw a null pointer exception
   *
   * @param object The object to check
   * @param message The message to put in the exception if null
   * @param <T> The type of the object
   * @return The object if not null
   * @throws NullPointerException If the object is null
   */
  public static <T> T checkNotNull(T object, String message) throws NullPointerException {
    if (object == null) throw new NullPointerException((message != null) ? message : "");
    return object;
  }

  /**
   * Same as {@link #checkNotNull(Object, String)} but without a message
   *
   * @param object The object to check if is null
   * @param <T> The type of the object
   * @return The object in case it's not null
   * @throws NullPointerException If the object is null
   */
  public static <T> T checkNotNull(T object) throws NullPointerException {
    return checkNotNull(object, null);
  }
}
