package com.fred.inventory.utils;

import org.junit.Test;

import static com.fred.inventory.utils.StringUtils.isBlank;
import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilsTest {
  @Test public void isBlank_shouldReturnTrueForNullStrings() {
    assertThat(isBlank(null)).isTrue();
  }

  @Test public void isBlank_shouldReturnTrueForEmptyStrings() {
    assertThat(isBlank("")).isTrue();
  }

  @Test public void isBlank_shouldReturnFalseForNonEmptyStrings() {
    assertThat(isBlank("some string")).isFalse();
  }
}
