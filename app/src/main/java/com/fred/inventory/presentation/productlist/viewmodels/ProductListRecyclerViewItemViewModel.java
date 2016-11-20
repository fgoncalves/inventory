package com.fred.inventory.presentation.productlist.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import com.fred.inventory.domain.models.Product;
import java.util.Date;

/**
 * View model for each item in the product list
 */

public interface ProductListRecyclerViewItemViewModel {
  void onBindViewHolder(Product product);

  ObservableField<String> productName();

  ObservableField<String> productQuantityLabel();

  ObservableInt quantity();

  ObservableInt progressBarVisibility();

  ObservableField<Date> expirationDate();

  ObservableInt expirationDateVisibility();
}
