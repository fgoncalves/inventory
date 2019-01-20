package com.fred.inventory.di;

import com.fred.inventory.RootModule;
import com.fred.inventory.presentation.supplies.adapters.SuppliesAdapterImpl;
import com.fred.inventory.presentation.supplies.viewmodels.SuppliesItemViewModel;
import com.fred.inventory.presentation.supplies.viewmodels.SuppliesItemViewModelImpl;
import dagger.Module;
import dagger.Provides;

@Module(injects = SuppliesAdapterImpl.ViewHolder.class, addsTo = RootModule.class)
public class SupplyListsItemModule {
  @Provides public SuppliesItemViewModel providesListOfProductListsItemViewModel(
      SuppliesItemViewModelImpl viewModel) {
    return viewModel;
  }
}
