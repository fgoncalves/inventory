package com.fred.inventory.presentation.productlist.views;

import android.support.annotation.NonNull;
import rx.Observable;

/**
 * Interface for the product list
 * <p/>
 * Created by fred on 29.03.16.
 */
public interface ProductListView {
  enum ViewInteractionType {
    ADD_PRODUCT_BUTTON_CLICKED, DISMISS
  }

  void doDismiss();

  void displayProductListName(@NonNull String name);

  void showEmptyProductList();

  void hideEmptyProductList();

  void showKeyboardOnProductListName();

  void showProductList(@NonNull String productListId);

  void hideKeyboard();

  Observable<ViewInteractionType> interactions();

  String getProductListName();

  void showEmptyProductListNameErrorMessage();
}
