package com.fred.inventory.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectsTest {

  @Test public void equals_shouldReturnTrueIfBothObjectsAreTheSameReference() {
    Integer a, b;
    a = b = 1234;

    boolean result = Objects.equals(a, b);

    assertThat(result).isTrue();
  }

  @Test public void equals_shouldReturnTrueIfBothObjectsAreNull() {
    Integer a, b;
    a = b = null;

    boolean result = Objects.equals(a, b);

    assertThat(result).isTrue();
  }

  @Test public void equals_shouldReturnFalseIfOneObjectIsNullAndTheOtherIsNot() {
    Integer a, b;
    a = null;
    b = 123;

    boolean result = Objects.equals(a, b);

    assertThat(result).isFalse();

    a = 123;
    b = null;

    result = Objects.equals(a, b);

    assertThat(result).isFalse();
  }

  @Test public void equals_shouldReturnTrueIfTheObjectsEqualsMethodEvaluatesToTrue() {
    Integer a, b;
    a = 1234567;
    b = 1234567;

    boolean result = Objects.equals(a, b);

    assertThat(result).isTrue();
  }
}