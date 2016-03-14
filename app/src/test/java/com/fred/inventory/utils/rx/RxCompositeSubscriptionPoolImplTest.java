package com.fred.inventory.utils.rx;

import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.subscriptions.CompositeSubscription;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RxCompositeSubscriptionPoolImplTest {
  @Mock Map<String, CompositeSubscription> subscriptionsCache;

  private RxCompositeSubscriptionPoolImpl compositeSubscriptionPool;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    compositeSubscriptionPool = new RxCompositeSubscriptionPoolImpl(subscriptionsCache);
  }

  @Test public void addSubscription_shouldCreateNewSubscriptionAndAssociateWithKey() {
    String key = "test";
    CompositeSubscription subscription = new CompositeSubscription();

    compositeSubscriptionPool.addSubscription(key, subscription);

    verify(subscriptionsCache).put(eq(key), any(CompositeSubscription.class));
  }

  @Test public void addSubscription_shouldCreateNewSubscriptionWhenPreviousOneIsUnusable() {
    CompositeSubscription compositeSubscription = new CompositeSubscription();
    compositeSubscription.unsubscribe();
    when(subscriptionsCache.get(anyString())).thenReturn(compositeSubscription);

    String key = "test";

    CompositeSubscription subscription = new CompositeSubscription();

    compositeSubscriptionPool.addSubscription(key, subscription);

    verify(subscriptionsCache).put(eq(key), any(CompositeSubscription.class));
  }

  @Test public void addSubscription_shouldNotCreateNewSubscriptionIfThePreviousIsStillUsable() {
    CompositeSubscription subscription = new CompositeSubscription();
    CompositeSubscription compositeSubscription = new CompositeSubscription();
    when(subscriptionsCache.get(anyString())).thenReturn(compositeSubscription);

    String key = "test";
    compositeSubscriptionPool.addSubscription(key, subscription);

    verify(subscriptionsCache, never()).put(eq(key), any(CompositeSubscription.class));
  }

  @Test public void unsubscribeFrom_shouldUnsubscribeFromPreviousSuscription() {
    CompositeSubscription compositeSubscription = new CompositeSubscription();
    when(subscriptionsCache.get(anyString())).thenReturn(compositeSubscription);

    compositeSubscriptionPool.unsubscribeFrom("foo");

    assertThat(compositeSubscription.isUnsubscribed()).isTrue();
  }

  @Test public void unsubscribeFrom_shouldNotBreakIfSubscriptionIsNotThere() {
    try {
      compositeSubscriptionPool.unsubscribeFrom("unknown key");
    } catch (Throwable e) {
      fail("Unsuscribed threw an unexpected exception and shouldn't have.", e);
    }
  }
}
