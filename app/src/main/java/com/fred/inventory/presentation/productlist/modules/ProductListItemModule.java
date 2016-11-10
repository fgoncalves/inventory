package com.fred.inventory.presentation.productlist.modules;

import com.fred.inventory.RootModule;
import com.fred.inventory.presentation.productlist.adapters.ProductListRecyclerViewAdapterImpl;
import com.fred.inventory.presentation.productlist.viewmodels.ProductListRecyclerViewItemViewModel;
import com.fred.inventory.presentation.productlist.viewmodels.ProductListRecyclerViewItemViewModelImpl;
import dagger.Module;
import dagger.Provides;

/**
 * Dagger module for each item in the product list
 * <p/>
 * Created by fred on 10.11.16.
 */
@Module(injects = {
    ProductListRecyclerViewAdapterImpl.ViewHolder.class
}, addsTo = RootModule.class) public class ProductListItemModule {
  @Provides
  public ProductListRecyclerViewItemViewModel providesProductListRecyclerViewItemViewModel(
      ProductListRecyclerViewItemViewModelImpl viewModel) {
    return viewModel;
  }
}
