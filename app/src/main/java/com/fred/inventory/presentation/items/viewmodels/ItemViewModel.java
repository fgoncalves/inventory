package com.fred.inventory.presentation.items.viewmodels;

import android.view.View;

/**
 * View model for an item, a.k.a. product.
 */
public interface ItemViewModel {
  void onAttachedToWindow();

  void onDetachedFromWindow();

  void forProductList(String productListId);

  void forProduct(String productId);

  void onEditExpireDateButtonClick(View view);
}
