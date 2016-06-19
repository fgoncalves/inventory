package com.fred.inventory.presentation.listofproducts.modules;

import com.fred.inventory.RootModule;
import com.fred.inventory.presentation.listofproducts.adapters.ListOfProductListsAdapter;
import com.fred.inventory.presentation.listofproducts.adapters.ListOfProductListsAdapterImpl;
import com.fred.inventory.presentation.listofproducts.viewmodels.ListOfProductListsViewModel;
import com.fred.inventory.presentation.listofproducts.viewmodels.ListOfProductListsViewModelImpl;
import com.fred.inventory.presentation.listofproducts.views.ListOfProductListsViewImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(injects = ListOfProductListsViewImpl.class, addsTo = RootModule.class)
public class ListOfProductListsModule {
  @Provides @Singleton public ListOfProductListsViewModel providesProductListsViewModel(
      ListOfProductListsViewModelImpl viewModel) {
    return viewModel;
  }

  @Provides @Singleton public ListOfProductListsAdapter providesListOfProductListsAdapter(
      ListOfProductListsAdapterImpl adapter) {
    return adapter;
  }
}
