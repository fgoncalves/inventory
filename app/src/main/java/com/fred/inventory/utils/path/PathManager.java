package com.fred.inventory.utils.path;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.transition.Transition;
import android.view.View;

/**
 * The path manager is the entity responsible for applying the screen transitions
 * <p/>
 * Created by fred on 08.11.15.
 */
public interface PathManager {
  String PATH = "path";

  /**
   * Go to the next screen with the appropriate animations between screens and shared elements.
   * The implementation will not use the shared animation if the sdk doesn't support them.
   *
   * @param from The fragment from where we are transitioning
   * @param to The fragment to where we are transitioning
   * @param enterTransition The animation for the fragment entering
   * @param enterSharedTransition The animation for the entering shared element
   * @param exitTransition The animation for the fragment exiting
   * @param exitSharedTransition The animation for the exiting shared element
   * @param sharedElement The shared element
   * @param sharedElementTransactionName The name to use as the shared element transaction
   * @param placeholderId The place holder id where the fragment should be put
   */
  void go(Fragment from, Fragment to, Transition enterTransition, Transition enterSharedTransition,
      Transition exitTransition, Transition exitSharedTransition, View sharedElement,
      String sharedElementTransactionName, @IdRes int placeholderId);

  /**
   * Convenience method for {@link #go(Fragment, Fragment, Transition, Transition, Transition,
   * Transition, View, String, int)}
   * which will simply transition without any custom animations or transitions.
   *
   * @param to The fragment to go to
   * @param placeholderId The placeholder where to put the fragment
   */
  void go(Fragment to, @IdRes int placeholderId);

  /**
   * Simply go back
   *
   * @return True if the back was handled. False otherwise
   */
  boolean back();

  /**
   * This method will clear the back stack and place the current fragment in the given placeholder.
   * This method is useful to start a clean start of the app.
   *
   * @param fragment The fragment to put in the place holder
   * @param placeholderId The place holder id
   */
  void single(Fragment fragment, @IdRes int placeholderId);

  /**
   * Get the last fragment added to the back stack
   *
   * @return The last fragment added to the back stack
   */
  Fragment top();

  /**
   * Save the current path in the out state under the name
   * {@link #PATH}
   *
   * @param outState The bundle where the path will be saved
   */
  void savePath(Bundle outState);

  /**
   * Restore the path from the given state
   *
   * @param savedState The bundle where the state has been saved
   */
  void restorePath(Bundle savedState, @IdRes int containerId);
}
