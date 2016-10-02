package com.fred.inventory.presentation.items.modules;

import com.fred.inventory.RootModule;
import com.fred.inventory.presentation.items.ItemScreen;
import com.fred.inventory.presentation.items.viewmodels.ItemViewModel;
import com.fred.inventory.presentation.items.viewmodels.ItemViewModelImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(injects = { ItemScreen.class }, addsTo = RootModule.class) public class ItemViewModule {
  @Provides @Singleton public ItemViewModel providesItemViewModel(ItemViewModelImpl viewModel) {
    return viewModel;
  }
}
