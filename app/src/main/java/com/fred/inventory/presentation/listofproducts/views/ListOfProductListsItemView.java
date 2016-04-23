package com.fred.inventory.presentation.listofproducts.views;

import android.support.annotation.NonNull;
import com.fred.inventory.domain.models.ProductList;

/**
 * The view for each item in the list of product lists
 */
public interface ListOfProductListsItemView {
  void displayProductListName(@NonNull String name);

  void displayProductList(@NonNull ProductList productList);

  void displayNumberOfProducts(int items);
}
