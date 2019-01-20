package com.fred.inventory.di;

import com.fred.inventory.RootModule;
import com.fred.inventory.data.firebase.models.SupplyItem;
import com.fred.inventory.presentation.supplieslist.SuppliesListScreen;
import com.fred.inventory.presentation.supplieslist.adapters.SuppliesListRecyclerViewAdapter;
import com.fred.inventory.presentation.supplieslist.adapters.SuppliesListRecyclerViewAdapterImpl;
import com.fred.inventory.presentation.supplieslist.adapters.comparators.SupplyItemNameComparator;
import com.fred.inventory.presentation.supplieslist.viewmodels.SuppliesListViewModel;
import com.fred.inventory.presentation.supplieslist.viewmodels.SuppliesListViewModelImpl;
import dagger.Module;
import dagger.Provides;
import java.util.Comparator;
import javax.inject.Singleton;

@Module(injects = { SuppliesListScreen.class }, addsTo = RootModule.class)
public class SuppliesListModule {
  @Provides @Singleton
  public SuppliesListViewModel providesProductListViewModel(SuppliesListViewModelImpl viewModel) {
    return viewModel;
  }

  @Provides @Singleton public SuppliesListRecyclerViewAdapter providesProductListRecyclerViewAdapter(
      SuppliesListRecyclerViewAdapterImpl adapter) {
    return adapter;
  }

  @Provides @Singleton public Comparator<SupplyItem> providesProductComparator(
      SupplyItemNameComparator supplyItemNameComparator) {
    return supplyItemNameComparator;
  }
}
