package com.fred.inventory.utils.rx;

import android.support.annotation.NonNull;
import rx.Subscription;

/**
 * A subscription pool so we can map proper cases to proper subscriptions and access them in
 * different places.
 * <p/>
 * The idea behind this class is to maintain references to several subcriptions so one can
 * unsubscribe if the app goes to the background
 * <p/>
 * The subscriptions held by this pool are composite subscriptions so one can add multiple
 * subscriptions with the same key and unsubscribe from all of them at once.
 * <p/>
 * Created on 13/11/15.
 */
public interface RxSubscriptionPool {

  /**
   * Add a subscription to this pool and associate it with the given key
   *
   * @param key The key to associate with the subscription
   * @param subscription The subscription to add
   */
  void addSubscription(@NonNull String key, @NonNull Subscription subscription);

  /**
   * Unsubscribe from all subscriptions held by the given key
   *
   * @param key The key associated with the subscriptions
   */
  void unsubscribeFrom(@NonNull String key);
}
