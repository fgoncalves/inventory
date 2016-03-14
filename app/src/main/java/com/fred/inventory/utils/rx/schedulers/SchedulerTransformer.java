package com.fred.inventory.utils.rx.schedulers;

import rx.Observable;

/**
 * A scheduler transformer is an rx transformer that changes the stream schedulers to other
 * schedulers.
 * <p/>
 * The idea behind this transformer is to remove a lot of boiler plate code and on top of that
 * make it easier for us to inject different scheduler transformers for testing as well the calls.
 */
public interface SchedulerTransformer {

  <T> Observable.Transformer<T, T> applySchedulers();
}
