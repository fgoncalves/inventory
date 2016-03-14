package com.fred.inventory.testhelpers;

import com.fred.inventory.utils.rx.schedulers.SchedulerTransformer;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * A Transformer which put an Observable on 'immediate' schedulers
 */
public class ImmediateToImmediateTransformer implements SchedulerTransformer {

  private final Scheduler immediate = Schedulers.immediate();

  @Override public <T> Observable.Transformer<T, T> applySchedulers() {
    return new Observable.Transformer<T, T>() {
      @Override public Observable<T> call(Observable<T> observable) {
        return observable.subscribeOn(immediate).observeOn(immediate);
      }
    };
  }
}
