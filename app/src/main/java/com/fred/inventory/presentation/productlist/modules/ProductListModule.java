package com.fred.inventory.presentation.productlist.modules;

import com.fred.inventory.RootModule;
import com.fred.inventory.presentation.productlist.presenters.ProductListPresenter;
import com.fred.inventory.presentation.productlist.presenters.ProductListPresenterImpl;
import com.fred.inventory.presentation.productlist.views.ProductListView;
import com.fred.inventory.presentation.productlist.views.ProductListViewImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(injects = { ProductListViewImpl.class }, addsTo = RootModule.class)
public class ProductListModule {
  private final ProductListView view;

  public ProductListModule(ProductListView view) {
    this.view = view;
  }

  @Provides @Singleton public ProductListView providesProductListView() {
    return view;
  }

  @Provides @Singleton
  public ProductListPresenter providesProductListPresenter(ProductListPresenterImpl presenter) {
    return presenter;
  }
}
