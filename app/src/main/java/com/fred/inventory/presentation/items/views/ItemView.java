package com.fred.inventory.presentation.items.views;

import android.support.annotation.NonNull;

/**
 * View shown to the user when we show the item
 */
public interface ItemView {
  void displayProductName(String name);

  void displayFailedToFetchProductListError();

  void showKeyboardOnItemTitle();

  void displayForProductList(@NonNull String productListId);
}
