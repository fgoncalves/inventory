package com.fred.inventory.presentation.globalsearch.modules;

import com.fred.inventory.RootModule;
import com.fred.inventory.presentation.globalsearch.adapters.GlobalSearchRecyclerViewAdapterImpl;
import com.fred.inventory.presentation.globalsearch.viewmodels.GlobalSearchListItemViewModel;
import com.fred.inventory.presentation.globalsearch.viewmodels.GlobalSearchListItemViewModelImpl;
import dagger.Module;
import dagger.Provides;

@Module(injects = {
    GlobalSearchRecyclerViewAdapterImpl.ViewHolder.class
}, addsTo = RootModule.class) public class GlobalSearchListItemModule {
  @Provides public GlobalSearchListItemViewModel providesGlobalSearchListItemViewModel(
      GlobalSearchListItemViewModelImpl viewModel) {
    return viewModel;
  }
}
