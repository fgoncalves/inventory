package com.fred.inventory.presentation.listofproducts.presenters;

import com.fred.inventory.domain.models.ProductList;

/**
 * The presenter for each item in the list of product lists
 */
public interface ListOfProductListsItemPresenter {
  void attachModel(ProductList productList);

  void onAttachedToWindow();
}
