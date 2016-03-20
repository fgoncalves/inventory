package com.fred.inventory.utils.path;

import android.app.Fragment;
import android.support.annotation.IdRes;
import android.transition.Transition;
import android.view.View;

/**
 * The path manager is the entity responsible for applying the screen transitions
 * <p/>
 * Created by fred on 08.11.15.
 */
public interface PathManager {
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
}
