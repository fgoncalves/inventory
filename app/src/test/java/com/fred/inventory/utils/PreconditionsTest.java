package com.fred.inventory.utils;

import org.junit.Test;

import static com.fred.inventory.utils.Preconditions.checkNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.assertj.core.api.Fail.failBecauseExceptionWasNotThrown;

public class PreconditionsTest {
  @Test public void checkNotNull_shouldThrowExceptionWhenNullIsPassed() {
    try {
      checkNotNull(null, null);
      failBecauseExceptionWasNotThrown(NullPointerException.class);
    } catch (NullPointerException ignored) {
    }
  }

  @Test public void checkNotNull_shouldThrowExceptionWithTheProperMessageWhenNullIsPassed() {
    String expected = "This is an expected exception";
    try {
      checkNotNull(null, expected);
      failBecauseExceptionWasNotThrown(NullPointerException.class);
    } catch (NullPointerException e) {
      assertThat(e).hasMessage(expected);
    }
  }

  @Test public void checkNotNull_shouldNotBreakAndReturnTheSameObjectIfNotNull() {
    Integer nine = 9;
    try {
      Integer result = checkNotNull(nine, null);
      assertThat(result).isEqualTo(nine);
    } catch (Throwable t) {
      fail("Unexpected exception", t);
    }
  }
}
