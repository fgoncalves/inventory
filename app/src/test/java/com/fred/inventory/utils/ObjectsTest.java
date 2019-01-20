package com.fred.inventory.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectsTest {
  class Bean {
    int a;

    public Bean(int a) {
      this.a = a;
    }

    @Override public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Bean)) return false;

      Bean bean = (Bean) o;

      return a == bean.a;
    }

    @Override public int hashCode() {
      return a;
    }
  }

  @Test public void equals_shouldReturnTrueIfObjectsAreTheSameReference() {
    String one = "foo";

    assertThat(Objects.equals(one, one)).isTrue();
  }

  @Test public void equals_shouldReturnTrueIfObjectsAreTheSameContent() {
    Bean one = new Bean(12);
    Bean two = new Bean(12);

    assertThat(Objects.equals(one, two)).isTrue();
  }

  @Test public void equals_shouldReturnFalseIfOneOfTheObjectsIsNull() {
    String two = "bar";

    assertThat(Objects.equals(null, two)).isFalse();
    assertThat(Objects.equals(two, null)).isFalse();
  }

  @Test public void equals_shouldReturnFalseIfObjectsAreDifferent() {
    String one = "foo";
    String two = "bar";

    assertThat(Objects.equals(one, two)).isFalse();
  }

  @Test public void equals_shouldReturnTrueIfBothAreNull() {
    assertThat(Objects.equals(null, null)).isTrue();
  }
}
