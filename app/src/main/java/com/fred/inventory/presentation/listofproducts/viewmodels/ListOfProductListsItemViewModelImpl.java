package com.fred.inventory.presentation.listofproducts.viewmodels;

import android.content.Context;
import android.databinding.ObservableField;
import com.fred.inventory.R;
import com.fred.inventory.domain.models.ProductList;
import javax.inject.Inject;

public class ListOfProductListsItemViewModelImpl implements ListOfProductListsItemViewModel {
  private final ObservableField<String> productListNameObservable = new ObservableField<>();
  private final ObservableField<String> infoTextObservable = new ObservableField<>();
  private final Context context;

  @Inject public ListOfProductListsItemViewModelImpl(Context context) {
    this.context = context;
  }

  @Override public void onBindViewHolder(ProductList productList) {
    productListNameObservable.set(productList.getName());
    infoTextObservable.set(
        context.getString(R.string.number_of_items, productList.getProducts().size()));
  }

  @Override public ObservableField<String> itemNameObservable() {
    return productListNameObservable;
  }

  @Override public ObservableField<String> infoTextObservable() {
    return infoTextObservable;
  }
}
