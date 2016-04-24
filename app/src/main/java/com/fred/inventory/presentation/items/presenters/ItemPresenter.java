package com.fred.inventory.presentation.items.presenters;

/**
 * Presenter for the view where we show the item
 */
public interface ItemPresenter {
  void onAttachedToWindow();

  void forProductList(String productListId);

  void onDetachedFromWindow();

  void forProduct(String productId);
}
