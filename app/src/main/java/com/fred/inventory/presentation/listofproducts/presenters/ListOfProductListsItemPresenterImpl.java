package com.fred.inventory.presentation.listofproducts.presenters;

import com.fred.inventory.domain.models.ProductList;
import com.fred.inventory.presentation.listofproducts.views.ListOfProductListsItemView;
import com.fred.inventory.utils.StringUtils;
import javax.inject.Inject;

public class ListOfProductListsItemPresenterImpl implements ListOfProductListsItemPresenter {
  private final ListOfProductListsItemView view;
  private ProductList productList;

  @Inject public ListOfProductListsItemPresenterImpl(ListOfProductListsItemView view) {
    this.view = view;
  }

  @Override public void attachModel(ProductList productList) {
    this.productList = productList;
    view.displayProductListName(StringUtils.valueOrDefault(productList.getName(), ""));
  }
}
