package com.fred.inventory.presentation.productlist.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;
import com.fred.inventory.domain.models.Product;

/**
 * View model for each item in the product list
 */

public interface ProductListRecyclerViewItemViewModel {
  interface OnDeleteListener {
    void onDelete();
  }

  interface OnItemClickListener {
    void onClicked();
  }

  void onBindViewHolder(Product product);

  void setOnDeleteListener(OnDeleteListener onDeleteListener);

  void setOnItemClickListener(OnItemClickListener onItemClickListener);

  ObservableField<String> productName();

  ObservableField<String> productQuantityLabel();

  ObservableInt quantity();

  ObservableInt progressBarVisibility();

  View.OnClickListener deleteButtonClickListener();

  View.OnClickListener itemClickListener();
}
