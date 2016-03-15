package com.fred.inventory.utils;

import java.util.UUID;

/**
 * Class which generates unique ids
 */
public class UniqueIdGenerator {
  /**
   * Create a unique random id
   *
   * @return A unique random id
   */
  public static String id() {
    return UUID.randomUUID().toString();
  }
}
