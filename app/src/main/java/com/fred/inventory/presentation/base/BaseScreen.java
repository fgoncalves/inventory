package com.fred.inventory.presentation.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Base fragment for the entire app. This should be implemented by all fragments in the app. It
 * contains base methods that can be used by all fragments.
 */
public abstract class BaseScreen extends Fragment {
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
    }
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
