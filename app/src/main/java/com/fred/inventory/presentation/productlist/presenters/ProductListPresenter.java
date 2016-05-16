package com.fred.inventory.presentation.productlist.presenters;

/**
 * The presenter for the product list view
 * <p/>
 * Created by fred on 29.03.16.
 */
public interface ProductListPresenter {
  void onAttachedToWindow();

  void onDetachedFromWindow();

  void forProductList(String productListId);

  void onDoneButtonClicked();

  void onAddProductButtonClicked();
}
