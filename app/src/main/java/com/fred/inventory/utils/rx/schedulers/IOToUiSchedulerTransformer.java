package com.fred.inventory.utils.rx.schedulers;

import javax.inject.Inject;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A {@link Observable.Transformer} to subscribe on worker thread and observe on main thread
 */
public class IOToUiSchedulerTransformer implements SchedulerTransformer {

  private final Scheduler subscribeOnScheduler = Schedulers.io();
  private final Scheduler observeOnScheduler = AndroidSchedulers.mainThread();

  @Inject public IOToUiSchedulerTransformer() {
  }

  @Override public <T> Observable.Transformer<T, T> applySchedulers() {
    return new Observable.Transformer<T, T>() {
      @Override public Observable<T> call(Observable<T> observable) {
        return observable.subscribeOn(subscribeOnScheduler).observeOn(observeOnScheduler);
      }
    };
  }
}
