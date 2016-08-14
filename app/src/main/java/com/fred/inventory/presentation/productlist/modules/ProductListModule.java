package com.fred.inventory.presentation.productlist.modules;

import com.fred.inventory.RootModule;
import com.fred.inventory.presentation.productlist.viewmodels.ProductListViewModel;
import com.fred.inventory.presentation.productlist.viewmodels.ProductListViewModelImpl;
import com.fred.inventory.presentation.productlist.views.ProductListViewImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(injects = { ProductListViewImpl.class }, addsTo = RootModule.class)
public class ProductListModule {
  @Provides @Singleton
  public ProductListViewModel providesProductListViewModel(ProductListViewModelImpl viewModel) {
    return viewModel;
  }
}
