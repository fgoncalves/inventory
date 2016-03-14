package com.fred.inventory.utils.rx;

import android.support.annotation.NonNull;
import java.util.Map;
import javax.inject.Inject;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class RxCompositeSubscriptionPoolImpl implements RxSubscriptionPool {
  private final Map<String, CompositeSubscription> subscriptionsCache;

  @Inject public RxCompositeSubscriptionPoolImpl(
      @CompositeSubscriptionCache Map<String, CompositeSubscription> subscriptionsCache) {
    this.subscriptionsCache = subscriptionsCache;
  }

  @Override public void addSubscription(@NonNull String key, @NonNull Subscription subscription) {
    CompositeSubscription compositeSubscription = subscriptionsCache.get(key);
    if (compositeSubscription == null || compositeSubscription.isUnsubscribed()) {
      compositeSubscription = new CompositeSubscription();
      subscriptionsCache.put(key, compositeSubscription);
    }
    compositeSubscription.add(subscription);
  }

  @Override public void unsubscribeFrom(@NonNull String key) {
    CompositeSubscription compositeSubscription = subscriptionsCache.get(key);
    if (compositeSubscription == null) {
      return;
    }

    compositeSubscription.unsubscribe();
  }
}
