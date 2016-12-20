package com.fred.inventory.presentation.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

/**
 * Base fragment for the entire app. This should be implemented by all fragments in the app. It
 * contains base methods that can be used by all fragments.
 */
public abstract class BaseScreen extends Fragment {
  protected static final int NO_RESOURCE_ID = 0;

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    if (!(getActivity() instanceof AppCompatActivity)) {
      throw new IllegalStateException(
          "Cannot create an instance of a BaseScreen without an AppCompatActivity");
    }
  }

  @Override public void onResume() {
    super.onResume();
    AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
    Toolbar toolbar = getToolbar();
    if (toolbar != null) {
      appCompatActivity.setSupportActionBar(toolbar);
      appCompatActivity.getSupportActionBar().setTitle(getToolbarTitle());
      appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(isHomeButtonSupported());
    }
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    int menuRes = getMenuResource();
    if (menuRes == NO_RESOURCE_ID) return;
    inflater.inflate(menuRes, menu);
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
}
