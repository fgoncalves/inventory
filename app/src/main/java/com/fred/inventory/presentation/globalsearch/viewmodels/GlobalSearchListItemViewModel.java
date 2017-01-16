package com.fred.inventory.presentation.globalsearch.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

/**
 * View model for each item in the global search list
 */

public interface GlobalSearchListItemViewModel {
  ObservableField<String> productName();

  ObservableField<String> productListName();

  ObservableField<String> productQuantityLabel();

  ObservableInt quantity();

  ObservableInt progressBarVisibility();

  View.OnClickListener itemClickListener();
}
