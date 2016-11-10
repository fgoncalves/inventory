package com.fred.inventory.presentation.productlist.viewmodels;

import android.databinding.ObservableField;
import com.fred.inventory.domain.models.Product;
import javax.inject.Inject;

public class ProductListRecyclerViewItemViewModelImpl
    implements ProductListRecyclerViewItemViewModel {
  private final ObservableField<String> productName = new ObservableField<>();
  private final ObservableField<String> productQuantityLabel = new ObservableField<>();

  @Inject public ProductListRecyclerViewItemViewModelImpl() {
  }

  @Override public void onBindViewHolder(Product product) {
    productName.set(product.getName());
    productQuantityLabel.set(product.getQuantityLabel());
  }

  @Override public ObservableField<String> productName() {
    return productName;
  }

  @Override public ObservableField<String> productQuantityLabel() {
    return productQuantityLabel;
  }
}
