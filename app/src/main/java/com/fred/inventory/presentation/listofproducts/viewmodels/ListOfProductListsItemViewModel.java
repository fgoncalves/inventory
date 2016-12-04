package com.fred.inventory.presentation.listofproducts.viewmodels;

import android.databinding.ObservableField;
import com.fred.inventory.domain.models.ProductList;

/**
 * View model for each item in the first list of products
 * <p/>
 * Created by fred on 04.06.16.
 */
public interface ListOfProductListsItemViewModel {
  void onBindViewHolder(ProductList productList);

  ObservableField<String> itemNameObservable();

  ObservableField<String> infoTextObservable();
}
