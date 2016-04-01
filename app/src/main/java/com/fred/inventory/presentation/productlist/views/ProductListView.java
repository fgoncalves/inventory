package com.fred.inventory.presentation.productlist.views;

import android.support.annotation.NonNull;

/**
 * Interface for the product list
 * <p/>
 * Created by fred on 29.03.16.
 */
public interface ProductListView {
  void displayProductListName(@NonNull String name);

  void showEmptyProductList();

  void hideEmptyProductList();
}
