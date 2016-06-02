package com.fred.inventory.utils;

import android.support.annotation.Nullable;

/**
 * Poor man's version of the Objects class with some of the methods back ported.
 * <p/>
 * Created by fred on 29.05.16.
 */
public class Objects {
  /**
   * Check if a equals b.
   *
   * @return True if both objects are equal or both are null. False otherwise.
   */
  public static boolean equals(@Nullable Object a, @Nullable Object b) {
    return (a == b) || (a != null && a.equals(b));
  }
}
