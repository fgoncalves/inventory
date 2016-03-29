package com.fred.inventory.presentation.productlist.presenters;

import com.fred.inventory.domain.usecases.GetProductListUseCase;
import com.fred.inventory.presentation.productlist.views.ProductListView;
import javax.inject.Inject;

/**
 * <p/>
 * Created by fred on 29.03.16.
 */
public class ProductListPresenterImpl implements ProductListPresenter {
  private final ProductListView view;
  private final GetProductListUseCase getProductListUseCase;

  @Inject public ProductListPresenterImpl(ProductListView view,
      GetProductListUseCase getProductListUseCase) {
    this.view = view;
    this.getProductListUseCase = getProductListUseCase;
  }
}
