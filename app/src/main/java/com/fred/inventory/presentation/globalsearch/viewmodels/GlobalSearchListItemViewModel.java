package com.fred.inventory.presentation.globalsearch.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;
import com.fred.inventory.domain.models.GlobalSearchResult;

/**
 * View model for each item in the global search list
 */

public interface GlobalSearchListItemViewModel {
  void onBindViewHolder(GlobalSearchResult globalSearchResult);

  ObservableField<String> productName();

  ObservableField<String> productListName();

  ObservableField<String> productQuantityLabel();

  ObservableInt quantity();

  ObservableInt progressBarVisibility();

  View.OnClickListener itemClickListener();
}
