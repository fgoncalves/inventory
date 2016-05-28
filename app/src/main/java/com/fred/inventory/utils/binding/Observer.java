package com.fred.inventory.utils.binding;

/**
 * Observer interface to use with {@link Observable}
 * <p/>
 * Created by fred on 28.05.16.
 */
public interface Observer<T> {
  /**
   * Triggered by the observable when it's value changes
   *
   * @param value The new value
   */
  void update(T value);
}
