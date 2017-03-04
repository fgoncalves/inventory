package com.fred.inventory.presentation.listofproducts.modules;

import com.fred.inventory.RootModule;
import com.fred.inventory.data.firebase.models.SuppliesList;
import com.fred.inventory.presentation.listofproducts.ListOfProductListsScreen;
import com.fred.inventory.presentation.listofproducts.adapters.ListOfProductListsAdapter;
import com.fred.inventory.presentation.listofproducts.adapters.ListOfProductListsAdapterImpl;
import com.fred.inventory.presentation.listofproducts.adapters.comparators.SuppliesListComparator;
import com.fred.inventory.presentation.listofproducts.viewmodels.ListOfProductListsViewModel;
import com.fred.inventory.presentation.listofproducts.viewmodels.ListOfProductListsViewModelImpl;
import dagger.Module;
import dagger.Provides;
import java.util.Comparator;
import javax.inject.Singleton;

@Module(injects = ListOfProductListsScreen.class, addsTo = RootModule.class)
public class ListOfProductListsModule {
  @Provides @Singleton public ListOfProductListsViewModel providesProductListsViewModel(
      ListOfProductListsViewModelImpl viewModel) {
    return viewModel;
  }

  @Provides @Singleton public ListOfProductListsAdapter providesListOfProductListsAdapter(
      ListOfProductListsAdapterImpl adapter) {
    return adapter;
  }

  @Provides @Singleton
  public Comparator<SuppliesList> providesProductListComparator(SuppliesListComparator comparator) {
    return comparator;
  }
}
