package com.fred.inventory.di;

import com.fred.inventory.RootModule;
import com.fred.inventory.data.firebase.models.SuppliesList;
import com.fred.inventory.presentation.supplies.SuppliesScreen;
import com.fred.inventory.presentation.supplies.adapters.SuppliesAdapter;
import com.fred.inventory.presentation.supplies.adapters.SuppliesAdapterImpl;
import com.fred.inventory.presentation.supplies.adapters.comparators.SuppliesListComparator;
import com.fred.inventory.presentation.supplies.viewmodels.SuppliesViewModel;
import com.fred.inventory.presentation.supplies.viewmodels.SuppliesViewModelImpl;
import dagger.Module;
import dagger.Provides;
import java.util.Comparator;
import javax.inject.Singleton;

@Module(injects = SuppliesScreen.class, addsTo = RootModule.class)
public class SupplyListsModule {
  @Provides @Singleton public SuppliesViewModel providesProductListsViewModel(
      SuppliesViewModelImpl viewModel) {
    return viewModel;
  }

  @Provides @Singleton public SuppliesAdapter providesListOfProductListsAdapter(
      SuppliesAdapterImpl adapter) {
    return adapter;
  }

  @Provides @Singleton
  public Comparator<SuppliesList> providesProductListComparator(SuppliesListComparator comparator) {
    return comparator;
  }
}
