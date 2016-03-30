package com.fred.inventory.utils.path;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.support.annotation.IdRes;
import android.transition.Transition;
import android.view.View;
import javax.inject.Inject;

public class PathManagerImpl implements PathManager {
  private final FragmentManager manager;

  @Inject public PathManagerImpl(FragmentManager manager) {
    this.manager = manager;
  }

  @Override public void single(Fragment fragment, @IdRes int placeholderId) {
    int backstackElementCount = manager.getBackStackEntryCount();
    while (backstackElementCount-- > 0) manager.popBackStack();

    manager.beginTransaction()
        .replace(placeholderId, fragment, fragment.getClass().getCanonicalName())
        .addToBackStack(fragment.getClass().getCanonicalName())
        .commit();
  }

  @Override public void go(Fragment from, Fragment to, Transition enterTransition,
      Transition enterSharedTransition, Transition exitTransition, Transition exitSharedTransition,
      View sharedElement, String sharedElementTransactionName, @IdRes int placeholderId) {
    FragmentTransaction fragmentTransaction = manager.beginTransaction();

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      if (exitSharedTransition != null) from.setSharedElementReturnTransition(exitSharedTransition);
      if (enterSharedTransition != null) to.setSharedElementEnterTransition(enterSharedTransition);

      if (exitTransition != null) from.setExitTransition(exitTransition);
      if (enterTransition != null) to.setEnterTransition(enterTransition);

      if (sharedElement != null && sharedElementTransactionName != null) {
        fragmentTransaction.addSharedElement(sharedElement, sharedElementTransactionName);
      }
    }

    fragmentTransaction.replace(placeholderId, to, to.getClass().getCanonicalName())
        .addToBackStack(to.getClass().getCanonicalName())
        .commit();
  }

  @Override public void go(Fragment to, @IdRes int placeholderId) {
    go(null, to, null, null, null, null, null, null, placeholderId);
  }

  @Override public boolean back() {
    if (manager.getBackStackEntryCount() == 1) return false;
    manager.popBackStack();
    return true;
  }

  @Override public Fragment top() {
    String tag = manager.getBackStackEntryAt(manager.getBackStackEntryCount() - 1).getName();
    return manager.findFragmentByTag(tag);
  }
}
