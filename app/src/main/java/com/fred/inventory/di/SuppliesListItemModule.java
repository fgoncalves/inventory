package com.fred.inventory.di;

import com.fred.inventory.RootModule;
import com.fred.inventory.presentation.supplieslist.adapters.SuppliesListRecyclerViewAdapterImpl;
import com.fred.inventory.presentation.supplieslist.viewmodels.SuppliesListRecyclerViewItemViewModel;
import com.fred.inventory.presentation.supplieslist.viewmodels.SuppliesListRecyclerViewItemViewModelImpl;
import dagger.Module;
import dagger.Provides;

/**
 * Dagger module for each item in the product list
 * <p/>
 * Created by fred on 10.11.16.
 */
@Module(injects = {
    SuppliesListRecyclerViewAdapterImpl.ViewHolder.class
}, addsTo = RootModule.class) public class SuppliesListItemModule {
  @Provides
  public SuppliesListRecyclerViewItemViewModel providesProductListRecyclerViewItemViewModel(
      SuppliesListRecyclerViewItemViewModelImpl viewModel) {
    return viewModel;
  }
}
