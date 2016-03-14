package com.fred.inventory.utils.rx;

import com.fred.inventory.utils.rx.schedulers.SchedulerTransformer;
import com.fred.inventory.utils.rx.schedulers.qualifiers.IOToUiSchedulerTransformer;
import dagger.Module;
import dagger.Provides;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;
import rx.subscriptions.CompositeSubscription;

@Module(complete = false, library = true) public class RxUtilsModule {
  @Provides @Singleton public RxSubscriptionPool providesRxCompositeSubscriptionPool(
      RxCompositeSubscriptionPoolImpl compositeSubscriptionPool) {
    return compositeSubscriptionPool;
  }

  @Provides @Singleton @CompositeSubscriptionCache
  public Map<String, CompositeSubscription> providesCompositeSubscriptionCache() {
    return new HashMap<>();
  }

  @Provides @Singleton @IOToUiSchedulerTransformer
  public SchedulerTransformer providesIOToUiSchedulerTransformer(
      com.fred.inventory.utils.rx.schedulers.IOToUiSchedulerTransformer transformer) {
    return transformer;
  }
}
