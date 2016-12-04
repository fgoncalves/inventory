package com.fred.inventory.presentation.listofproducts.viewmodels;

import android.databinding.ObservableInt;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * View model for the list of product lists
 */
public interface ListOfProductListsViewModel {
  void onAttachedToWindow();

  ObservableInt emptyViewVisibilityObservable();

  ObservableInt listVisibilityObservable();

  RecyclerView.Adapter adapter();

  View.OnClickListener addButtonClickListener();
}
