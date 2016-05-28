package com.fred.inventory.utils.binding;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class ObservableTest {
  @Mock Observer<Integer> observer1;
  @Mock Observer<Integer> observer2;

  private Observable<Integer> observable;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    observable = Observable.create();
    observable.bind(observer1).bind(observer2);
  }

  @Test public void set_shouldUpdateObservers() {
    int expected = 1029284;

    observable.set(expected);

    verify(observer1).update(expected);
    verify(observer2).update(expected);
  }

  @Test public void set_shouldUpdateOneObserverButNotTheOneAlreadyUnbound() {
    observable.unbind(observer1);
    int expected = 1029284;

    observable.set(expected);

    verify(observer1, never()).update(expected);
    verify(observer2).update(expected);
  }
}