package com.fred.inventory.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.assertj.core.api.Fail.failBecauseExceptionWasNotThrown;

public class StringUtilsTest {
  @Test public void isBlank_shouldReturnTrueForNullInput() {
    boolean result = StringUtils.isBlank(null);

    assertThat(result).isTrue();
  }

  @Test public void isBlank_shouldReturnTrueForEmptyText() {
    boolean result = StringUtils.isBlank("");

    assertThat(result).isTrue();
  }

  @Test public void isBlank_shouldReturnFalseForNonEmptyText() {
    boolean result = StringUtils.isBlank("foo");

    assertThat(result).isFalse();
  }

  @Test public void valueOrDefault_shouldReturnValueWhenNotEmpty() {
    String expected = "foo";

    CharSequence result = StringUtils.valueOrDefault(expected, "wrong value");

    assertThat(result).isEqualTo(expected);
  }

  @Test public void valueOrDefault_shouldReturnDefaultWhenEmpty() {
    String expected = "foo";

    CharSequence result = StringUtils.valueOrDefault(null, expected);

    assertThat(result).isEqualTo(expected);
  }

  @Test public void isPresent_shouldThrowIllegalArgumentExceptionWhenValueIsBlank() {
    try {
      StringUtils.isPresent("", "");
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    } catch (IllegalArgumentException ignored) {
    }
  }

  @Test public void isPresent_shouldNotAnyExceptionWhenValueIsNotBlank() {
    try {
      StringUtils.isPresent("some value", "");
    } catch (Throwable e) {
      fail("Expected no exception but got one", e);
    }
  }
}