package com.fred.inventory.presentation.listofproducts.presenters;

/**
 * Presenter for the list of products
 * <p/>
 * Created by fred on 13.03.16.
 */
public interface ListOfProductListsPresenter {
  void onAttachedToWindow();

  void onDetachedFromWindow();

  void onAddButtonClicked();
}
