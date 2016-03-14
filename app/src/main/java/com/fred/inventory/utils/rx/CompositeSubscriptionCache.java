package com.fred.inventory.utils.rx;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Qualifier;

/**
 * Dagger qualifier to use when injecting the Rx composite subscriptions cache
 */
@Qualifier @Retention(RetentionPolicy.RUNTIME) public @interface CompositeSubscriptionCache {
}
