package com.fred.inventory.presentation.productlist.viewmodels;

import android.databinding.ObservableField;
import com.fred.inventory.domain.models.Product;

/**
 * View model for each item in the product list
 */

public interface ProductListRecyclerViewItemViewModel {
  void onBindViewHolder(Product product);

  ObservableField<String> productName();

  ObservableField<String> productQuantityLabel();
}
