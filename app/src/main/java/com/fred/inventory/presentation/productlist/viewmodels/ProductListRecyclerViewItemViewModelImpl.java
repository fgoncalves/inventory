package com.fred.inventory.presentation.productlist.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;
import com.fred.inventory.domain.models.Product;
import java.util.Date;
import javax.inject.Inject;

public class ProductListRecyclerViewItemViewModelImpl
    implements ProductListRecyclerViewItemViewModel {
  private final ObservableField<String> productName = new ObservableField<>();
  private final ObservableField<String> productQuantityLabel = new ObservableField<>();
  private final ObservableInt quantity = new ObservableInt();
  private final ObservableInt progressBarVisibility = new ObservableInt();
  private final ObservableField<Date> expirationDate = new ObservableField<>();
  private final ObservableInt expirationDateVisibility = new ObservableInt();

  @Inject public ProductListRecyclerViewItemViewModelImpl() {
  }

  @Override public void onBindViewHolder(Product product) {
    productName.set(product.getName());
    productQuantityLabel.set(product.getQuantityLabel());
    quantity.set(product.getQuantity());
    if (product.isUnit()) {
      progressBarVisibility.set(View.GONE);
    } else {
      progressBarVisibility.set(View.VISIBLE);
    }
    if (product.getExpirationDate() != null) {
      expirationDate.set(product.getExpirationDate());
      expirationDateVisibility.set(View.VISIBLE);
    } else {
      expirationDateVisibility.set(View.GONE);
    }
  }

  @Override public ObservableField<String> productName() {
    return productName;
  }

  @Override public ObservableField<String> productQuantityLabel() {
    return productQuantityLabel;
  }

  @Override public ObservableInt quantity() {
    return quantity;
  }

  @Override public ObservableInt progressBarVisibility() {
    return progressBarVisibility;
  }

  @Override public ObservableField<Date> expirationDate() {
    return expirationDate;
  }

  @Override public ObservableInt expirationDateVisibility() {
    return expirationDateVisibility;
  }
}
