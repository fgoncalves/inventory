package com.fred.inventory.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Class which generates unique ids
 */
public class UniqueIdGenerator {
  private static SecureRandom random = new SecureRandom();

  /**
   * Create a unique random id
   *
   * @return A unique random id
   */
  public static String id() {
    return new BigInteger(130, random).toString(32);
  }
}
