package com.fred.inventory.presentation.productlist.modules;

import com.fred.inventory.RootModule;
import com.fred.inventory.presentation.productlist.ProductListScreen;
import com.fred.inventory.presentation.productlist.viewmodels.ProductListViewModel;
import com.fred.inventory.presentation.productlist.viewmodels.ProductListViewModelImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(injects = { ProductListScreen.class }, addsTo = RootModule.class)
public class ProductListModule {
  @Provides @Singleton
  public ProductListViewModel providesProductListViewModel(ProductListViewModelImpl viewModel) {
    return viewModel;
  }
}
