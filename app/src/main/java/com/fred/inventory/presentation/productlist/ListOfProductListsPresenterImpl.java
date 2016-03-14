package com.fred.inventory.presentation.productlist;

import com.fred.inventory.domain.usecases.ListAllProductListsUseCase;
import javax.inject.Inject;

public class ListOfProductListsPresenterImpl implements ListOfProductListsPresenter {
  private final ListOfProductListsView view;
  private final ListAllProductListsUseCase listAllProductListsUseCase;

  @Inject public ListOfProductListsPresenterImpl(ListOfProductListsView view,
      ListAllProductListsUseCase listAllProductListsUseCase) {
    this.view = view;
    this.listAllProductListsUseCase = listAllProductListsUseCase;
  }
}
