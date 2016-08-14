package com.fred.inventory.presentation.productlist.views;

import android.support.annotation.NonNull;
import com.fred.inventory.presentation.base.ViewInteraction;
import rx.Observable;

/**
 * Interface for the product list
 * <p/>
 * Created by fred on 29.03.16.
 */
public interface ProductListView {
  void showProductList(@NonNull String productListId);

  void showKeyboardOnProductListName();

  Observable<ViewInteraction> interactions();
}
