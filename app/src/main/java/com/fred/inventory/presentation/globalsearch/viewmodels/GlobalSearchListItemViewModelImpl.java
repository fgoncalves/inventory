package com.fred.inventory.presentation.globalsearch.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;
import com.fred.inventory.domain.models.GlobalSearchResult;
import javax.inject.Inject;

public class GlobalSearchListItemViewModelImpl implements GlobalSearchListItemViewModel {
  private final ObservableField<String> productName = new ObservableField<>("");
  private final ObservableField<String> productListName = new ObservableField<>("");
  private final ObservableField<String> productQuantityLabel = new ObservableField<>("");
  private final ObservableInt quantity = new ObservableInt(0);
  private final ObservableInt progressBarVisibility = new ObservableInt(View.INVISIBLE);
  private final View.OnClickListener itemClickListener = view -> {
    // TODO: Start the product list
  };

  @Inject public GlobalSearchListItemViewModelImpl() {
  }

  @Override public void onBindViewHolder(GlobalSearchResult globalSearchResult) {
    productListName.set(globalSearchResult.getProductListName());
    productName.set(globalSearchResult.getProduct().getName());
    productQuantityLabel.set(globalSearchResult.getProduct().getQuantityLabel());
    quantity.set(globalSearchResult.getProduct().getQuantity());
    if (globalSearchResult.getProduct().isUnit()) {
      progressBarVisibility.set(View.INVISIBLE);
    } else {
      progressBarVisibility.set(View.VISIBLE);
    }
  }

  @Override public ObservableField<String> productName() {
    return productName;
  }

  @Override public ObservableField<String> productListName() {
    return productListName;
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

  @Override public View.OnClickListener itemClickListener() {
    return itemClickListener;
  }
}
