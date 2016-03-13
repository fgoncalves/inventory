package com.fred.inventory.presentation.productlist;

import javax.inject.Inject;

public class ListOfProductListsPresenterImpl implements ListOfProductListsPresenter {
  private final ListOfProductListsView view;

  @Inject public ListOfProductListsPresenterImpl(ListOfProductListsView view) {
    this.view = view;
  }
}
