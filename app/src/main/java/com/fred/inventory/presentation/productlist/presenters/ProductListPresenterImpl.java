package com.fred.inventory.presentation.productlist.presenters;

import com.fred.inventory.presentation.productlist.views.ProductListView;
import javax.inject.Inject;

/**
 * <p/>
 * Created by fred on 29.03.16.
 */
public class ProductListPresenterImpl implements ProductListPresenter {
  @Inject ProductListView view;

  @Inject public ProductListPresenterImpl(ProductListView view) {
    this.view = view;
  }
}
