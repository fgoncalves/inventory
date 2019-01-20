package com.fred.inventory.presentation.supplies.viewmodels;

import android.databinding.ObservableInt;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * View model for the list of product lists
 */
public interface SuppliesViewModel {
  void onResume();

  ObservableInt emptyViewVisibilityObservable();

  ObservableInt listVisibilityObservable();

  ObservableInt loadingVisibility();

  RecyclerView.Adapter<?> adapter();

  View.OnClickListener addButtonClickListener();

  void onPause();
}
