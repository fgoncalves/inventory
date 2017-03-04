package com.fred.inventory.presentation.productlist.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;
import com.fred.inventory.data.firebase.models.SuppliesList;
import com.fred.inventory.data.firebase.models.SupplyItem;

/**
 * View model for each item in the product list
 */

public interface ProductListRecyclerViewItemViewModel {
  interface OnItemClickListener {
    void onClicked();
  }

  void onBindViewHolder(SuppliesList suppliesList, SupplyItem supplyItem);

  void setOnItemClickListener(OnItemClickListener onItemClickListener);

  ObservableField<String> productName();

  ObservableField<String> productQuantityLabel();

  ObservableInt quantity();

  ObservableInt progressBarVisibility();

  View.OnClickListener deleteButtonClickListener();

  View.OnClickListener itemClickListener();
}
