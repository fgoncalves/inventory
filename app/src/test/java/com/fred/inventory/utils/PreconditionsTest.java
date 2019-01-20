package com.fred.inventory.utils;

import org.junit.Test;

import static com.fred.inventory.utils.Preconditions.checkNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.failBecauseExceptionWasNotThrown;

public class PreconditionsTest {

  @Test
  public void checkNotNull_shouldThrowNullPointerExceptionIfArgumentIsNullWithEmptyMessageWhenNullIsSpecified() {
    try {
      checkNotNull(null, null);
      failBecauseExceptionWasNotThrown(NullPointerException.class);
    } catch (NullPointerException e) {
      assertThat(e).hasMessage("");
    }
  }

  @Test
  public void checkNotNull_shouldThrowNullPointerExceptionIfArgumentIsNullWithSpecifiedMessage() {
    String expectedMessage = "Some message to put in the exception";
    try {
      checkNotNull(null, expectedMessage);
      failBecauseExceptionWasNotThrown(NullPointerException.class);
    } catch (NullPointerException e) {
      assertThat(e).hasMessage(expectedMessage);
    }
  }

  @Test public void checkNonNull_shouldReturnTheSameObjectIfItIsNotNull() {
    Integer expected = 123;

    Integer result = checkNotNull(expected, null);

    assertThat(result).isEqualTo(expected);
  }
}
