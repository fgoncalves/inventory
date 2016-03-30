package com.fred.inventory.presentation.base;

import android.app.Fragment;
import com.fred.inventory.presentation.navigation.NavigationListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Base fragment for the entire app. This should be implemented by all fragments in the app. It
 * contains base methods that can be used by all fragments.
 */
public abstract class BaseScreen extends Fragment {
  protected List<NavigationListener> navigationListeners = new ArrayList<>();

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
}
