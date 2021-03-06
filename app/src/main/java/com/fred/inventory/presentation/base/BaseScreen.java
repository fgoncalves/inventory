package com.fred.inventory.presentation.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.fred.inventory.MainActivity;
import com.fred.inventory.R;
import com.fred.inventory.utils.path.PathManager;
import javax.inject.Inject;

/**
 * Base fragment for the entire app. This should be implemented by all fragments in the app. It
 * contains base methods that can be used by all fragments.
 */
public abstract class BaseScreen extends Fragment {
  protected static final int NO_RESOURCE_ID = 0;

  @Inject PathManager pathManager;

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    if (!(getActivity() instanceof AppCompatActivity)) {
      throw new IllegalStateException(
          "Cannot create an instance of a BaseScreen without an AppCompatActivity");
    }
    setHasOptionsMenu(true);
  }

  @Override public void onResume() {
    super.onResume();
    MainActivity mainActivity = (MainActivity) getActivity();
    DrawerLayout drawerLayout = mainActivity.getDrawerLayout();
    boolean supportsDrawer = supportsDrawer();

    if (supportsDrawer) {
      drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.START);
    } else {
      drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.START);
    }

    Toolbar toolbar = getToolbar();
    if (toolbar != null) {
      mainActivity.setSupportActionBar(toolbar);
      mainActivity.getSupportActionBar().setTitle(getToolbarTitle());

      if (supportsDrawer) {
        ActionBarDrawerToggle toggle =
            new ActionBarDrawerToggle(mainActivity, drawerLayout, toolbar, R.string.open_drawer,
                R.string.close_drawer);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
      } else {
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(isHomeButtonSupported());
      }
    }
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    int menuRes = getMenuResource();
    if (menuRes == NO_RESOURCE_ID) return;
    inflater.inflate(menuRes, menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      pathManager.back();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * Get the resource id for the menu
   *
   * @return The resource id for the toolbar menu
   */
  protected abstract int getMenuResource();

  /**
   * Check if the toolbar should have a home button
   *
   * @return True if the home button should be displayed. False otherwise.
   */
  protected abstract boolean isHomeButtonSupported();

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
   * Get the toolbar to put in the action bar
   *
   * @return The toolbar for the action bar
   */
  protected abstract Toolbar getToolbar();

  /**
   * Get the title to put in the action bar
   *
   * @return The title for the action bar
   */
  protected abstract String getToolbarTitle();

  /**
   * Check if the screen supports a drawer. If it does we'll set the toolbar home button as an
   * hamburguer icon.
   *
   * @return True if the drawer is supported
   */
  protected abstract boolean supportsDrawer();
}
