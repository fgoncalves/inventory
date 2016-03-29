package com.fred.inventory.testhelpers.matchers;

import org.mockito.ArgumentMatcher;
import rx.Subscription;

import static org.mockito.Matchers.argThat;

public class SubscriptionMatchers {

  /**
   * Match any argument of the type subscription
   */
  public static Subscription anySubscription() {
    return argThat(new InstanceOfSubscriptionMatcher());
  }

  private static class InstanceOfSubscriptionMatcher implements ArgumentMatcher<Subscription> {
    @Override public boolean matches(Object argument) {
      // Same behavior as the internal implementation of mockito, but here it's not internal
      return argument != null && Subscription.class.isAssignableFrom(argument.getClass());
    }
  }
}
