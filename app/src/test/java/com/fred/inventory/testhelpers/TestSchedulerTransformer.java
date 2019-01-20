package com.fred.inventory.testhelpers;

import com.fred.inventory.utils.rx.schedulers.SchedulerTransformer;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Test scheduler transformer that puts all observables in the trampoline schedule as well as the
 * subscribers.
 */

public class TestSchedulerTransformer implements SchedulerTransformer {
  @Override public <T> Observable.Transformer<T, T> applySchedulers() {
    return tObservable -> tObservable.observeOn(Schedulers.trampoline())
        .subscribeOn(Schedulers.trampoline());
  }
}
