package com.fred.inventory.di;

import com.fred.inventory.RootModule;
import com.fred.inventory.presentation.items.SupplyItemScreen;
import com.fred.inventory.presentation.items.viewmodels.SupplyItemViewModel;
import com.fred.inventory.presentation.items.viewmodels.SupplyItemViewModelImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(injects = { SupplyItemScreen.class }, addsTo = RootModule.class) public class SupplyItemViewModule {
  @Provides @Singleton public SupplyItemViewModel providesItemViewModel(SupplyItemViewModelImpl viewModel) {
    return viewModel;
  }
}
