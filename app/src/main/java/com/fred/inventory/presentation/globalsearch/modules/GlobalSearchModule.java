package com.fred.inventory.presentation.globalsearch.modules;

import com.fred.inventory.RootModule;
import com.fred.inventory.presentation.globalsearch.GlobalSearchScreen;
import com.fred.inventory.presentation.globalsearch.adapters.GlobalSearchRecyclerViewAdapter;
import com.fred.inventory.presentation.globalsearch.adapters.GlobalSearchRecyclerViewAdapterImpl;
import com.fred.inventory.presentation.globalsearch.viewmodels.GlobalSearchViewModel;
import com.fred.inventory.presentation.globalsearch.viewmodels.GlobalSearchViewModelImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(injects = { GlobalSearchScreen.class }, addsTo = RootModule.class)
public class GlobalSearchModule {
  @Provides @Singleton
  public GlobalSearchRecyclerViewAdapter providesGlobalSearchRecyclerViewAdapter(
      GlobalSearchRecyclerViewAdapterImpl adapter) {
    return adapter;
  }

  @Provides @Singleton
  public GlobalSearchViewModel providesGlobalSearchViewModel(GlobalSearchViewModelImpl viewModel) {
    return viewModel;
  }
}
