package com.fred.inventory.presentation.base;

import android.app.Fragment;
import com.fred.inventory.presentation.navigation.NavigationListener;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Base fragment for the entire app. This should be implemented by all fragments in the app. It
 * contains base methods that can be used by all fragments.
 */
public abstract class BaseScreen extends Fragment {
  public enum LifeCycle {
    ON_RESUME
  }

  public enum ScreenEvent {
    REMOVE,
    NOOP
  }

  private PublishSubject<LifeCycle> lifeCyclePublisher = PublishSubject.create();
  protected List<NavigationListener> navigationListeners = new ArrayList<>();

  public Observable<LifeCycle> lifeCycle() {
    return lifeCyclePublisher;
  }

  @Override public void onResume() {
    super.onResume();
    lifeCyclePublisher.onNext(LifeCycle.ON_RESUME);
  }

  /**
   * Add a given listener to the navigation listener list of this fragment.
   *
   * @param listener The listener to add
   */
  public void addNavigationListener(NavigationListener listener) {
    navigationListeners.add(listener);
  }

  /**
   * Remove the given listener from the navigation listeners of this fragment
   *
   * @param listener The listener to remove
   */
  public void removeNavigationListener(NavigationListener listener) {
    navigationListeners.remove(listener);
  }

  /**
   * Call this method to handle the back press
   *
   * @return True if the back press was handled. False otherwise.
   */
  public boolean onBackPressed() {
    return handleBackPress();
  }

  /**
   * Delegate the back press handling to the subclasses
   *
   * @return True if the back press was handled
   */
  protected abstract boolean handleBackPress();

  /**
   * Stream of events related with the screen that can be exposed to the outside
   * and reacted upon
   *
   * @return Observable for the screen events
   */
  public abstract Observable<ScreenEvent> screenEvents();
}
