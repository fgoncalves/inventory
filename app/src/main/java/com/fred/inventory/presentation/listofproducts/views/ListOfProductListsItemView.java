package com.fred.inventory.presentation.listofproducts.views;

import android.support.annotation.NonNull;
import com.fred.inventory.domain.models.ProductList;

/**
 * The view for each item in the list of product lists
 */
public interface ListOfProductListsItemView {
  void displayProductList(@NonNull ProductList productList);
}
